# Modularization Architecture

## Table of Contents
- [Overview](#overview)
- [API and Implementation Separation](#api-and-implementation-separation)
- [Data Layer Modularization](#data-layer-modularization)
- [Testing Module Strategy](#testing-module-strategy)
- [Module Dependency Guidelines](#module-dependency-guidelines)
- [Benefits and Trade-offs](#benefits-and-trade-offs)
- [Best Practices](#best-practices)
- [Common Anti-Patterns](#common-anti-patterns)

## Overview

Modularization is a fundamental architectural pattern that breaks down a monolithic application into smaller, focused, and independent modules. The Findfin project employs a sophisticated modularization strategy with 70+ modules to achieve scalability, maintainability, and team productivity.

### Key Modularization Principles

1. **Single Responsibility**: Each module has one clear purpose
2. **Loose Coupling**: Modules depend on abstractions, not implementations
3. **High Cohesion**: Related functionality is grouped together
4. **Dependency Inversion**: Higher-level modules don't depend on lower-level modules
5. **Interface Segregation**: Modules expose only what consumers need

### Module Types in Findfin

```
┌─────────────────┐
│   App Module    │ ◄─── Main application module
└─────────┬───────┘
          │
┌─────────▼───────┐
│ Feature Modules │ ◄─── Business feature modules
└─────────┬───────┘
          │
┌─────────▼───────┐
│  Core Modules   │ ◄─── Shared infrastructure
└─────────┬───────┘
          │
┌─────────▼───────┐
│Library Modules  │ ◄─── Reusable utilities
└─────────────────┘
```

## API and Implementation Separation

### Why Separate API and Implementation?

The API/Implementation pattern provides a clear contract between modules while hiding implementation details. This pattern is evident in modules like `core/locale/api` and `core/locale/impl`.

#### 1. **Dependency Inversion Principle**

```kotlin
// core/locale/api/src/main/kotlin/LocaleProvider.kt
interface LocaleProvider {
    fun getCurrentLocale(): Locale
    fun getSupportedLocales(): List<Locale>
    fun setLocale(locale: Locale)
    fun observeLocaleChanges(): Flow<Locale>
}

// core/locale/impl/src/main/kotlin/LocaleProviderImpl.kt
@Singleton
class LocaleProviderImpl @Inject constructor(
    private val context: Context,
    private val preferences: SharedPreferences
) : LocaleProvider {
    
    override fun getCurrentLocale(): Locale {
        val savedLocale = preferences.getString(LOCALE_KEY, null)
        return if (savedLocale != null) {
            Locale.forLanguageTag(savedLocale)
        } else {
            ConfigurationCompat.getLocales(context.resources.configuration)[0]
        }
    }
    
    override fun observeLocaleChanges(): Flow<Locale> {
        return callbackFlow {
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == LOCALE_KEY) {
                    trySend(getCurrentLocale())
                }
            }
            preferences.registerOnSharedPreferenceChangeListener(listener)
            
            // Send initial value
            trySend(getCurrentLocale())
            
            awaitClose {
                preferences.unregisterOnSharedPreferenceChangeListener(listener)
            }
        }
    }
}
```

#### 2. **Testability and Mocking**

```kotlin
// feature/auth/presentation/src/test/kotlin/AuthViewModelTest.kt
class AuthViewModelTest {
    
    @Mock
    private lateinit var localeProvider: LocaleProvider // Mock the API, not implementation
    
    @Mock
    private lateinit var authRepository: AuthRepository
    
    private lateinit var viewModel: AuthViewModel
    
    @Test
    fun `should display login form in user's locale`() = runTest {
        // Given
        val userLocale = Locale.forLanguageTag("zh-HK")
        whenever(localeProvider.getCurrentLocale()).thenReturn(userLocale)
        
        // When
        viewModel = AuthViewModel(authRepository, localeProvider)
        
        // Then
        val uiState = viewModel.uiState.first()
        assertEquals(userLocale, uiState.displayLocale)
    }
}
```

#### 3. **Implementation Flexibility**

```kotlin
// Different implementations for different needs

// Production implementation
class LocaleProviderImpl : LocaleProvider {
    // Real implementation using SharedPreferences
}

// Testing implementation  
class TestLocaleProvider : LocaleProvider {
    private var currentLocale = Locale.US
    
    override fun getCurrentLocale() = currentLocale
    override fun setLocale(locale: Locale) { currentLocale = locale }
}

// Preview implementation for Compose Previews
class PreviewLocaleProvider : LocaleProvider {
    override fun getCurrentLocale() = Locale.getDefault()
    override fun observeLocaleChanges() = flowOf(Locale.getDefault())
}
```

#### 4. **Hilt Module Binding**

```kotlin
// core/locale/impl/src/main/kotlin/LocaleModule.kt
@Module
@InstallIn(SingletonComponent::class)
abstract class LocaleModule {
    
    @Binds
    abstract fun bindLocaleProvider(
        impl: LocaleProviderImpl
    ): LocaleProvider
}

// Consumer modules only depend on the API
// feature/auth/presentation/build.gradle.kts
dependencies {
    implementation(projects.core.locale.api) // ✅ Depend on API
    // NOT: implementation(projects.core.locale.impl) // ❌ Don't depend on implementation
}
```

### Benefits of API/Implementation Separation

1. **Reduced Compilation Time**: Changes in implementation don't trigger recompilation of consumers
2. **Parallel Development**: Teams can work on API contracts while implementations are developed
3. **Easier Testing**: Mock APIs instead of complex implementations
4. **Implementation Swapping**: Change implementations without affecting consumers
5. **Clear Contracts**: API modules define clear boundaries and responsibilities

## Data Layer Modularization

The data layer is split into multiple focused modules: `repo`, `remote-*`, and `local-*`. This pattern is evident in the bank feature structure:

```
feature/bank/data/
├── local-database/     # Local persistence
├── remote-network/     # Network data access  
└── repo/              # Data coordination
```

### Why Separate Repository, Remote, and Local Modules?

#### 1. **Single Responsibility Principle**

Each module has a focused responsibility:

```kotlin
// feature/bank/data/local-database/src/main/kotlin/BankLocalDataSource.kt
@Singleton
class BankLocalDataSource @Inject constructor(
    private val bankDao: BankDao
) {
    // ONLY responsible for local database operations
    suspend fun getBanks(): List<BankEntity> = bankDao.getAllBanks()
    
    suspend fun insertBanks(banks: List<BankEntity>) = bankDao.insertAll(banks)
    
    fun observeBanks(): Flow<List<BankEntity>> = bankDao.observeAllBanks()
    
    suspend fun getBanksNearLocation(
        lat: Double, 
        lng: Double, 
        radius: Double
    ): List<BankEntity> = bankDao.getBanksNearLocation(lat, lng, radius)
}

// feature/bank/data/remote-network/src/main/kotlin/BankRemoteDataSource.kt
@Singleton  
class BankRemoteDataSource @Inject constructor(
    private val bankApi: BankApi
) {
    // ONLY responsible for network operations
    suspend fun getBanks(): List<BankDto> = bankApi.getBanks()
    
    suspend fun getBankById(id: String): BankDto = bankApi.getBank(id)
    
    suspend fun searchBanks(query: String): List<BankDto> = bankApi.searchBanks(query)
    
    suspend fun getBanksNearLocation(
        lat: Double, 
        lng: Double, 
        radius: Double
    ): List<BankDto> = bankApi.getBanksNearLocation(lat, lng, radius)
}

// feature/bank/data/repo/src/main/kotlin/BankRepositoryImpl.kt
@Singleton
class BankRepositoryImpl @Inject constructor(
    private val localDataSource: BankLocalDataSource,
    private val remoteDataSource: BankRemoteDataSource,
    private val bankMapper: BankMapper,
    private val networkConnectivity: NetworkConnectivity,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BankRepository {
    // ONLY responsible for coordinating data sources and business logic
    
    override fun getBanks(): Flow<Result<List<Bank>>> {
        return localDataSource.observeBanks()
            .map { entities -> 
                Result.success(entities.map { bankMapper.entityToDomain(it) })
            }
            .onStart {
                if (networkConnectivity.isConnected()) {
                    refreshBanksFromRemote()
                }
            }
            .flowOn(ioDispatcher)
    }
    
    private suspend fun refreshBanksFromRemote() {
        try {
            val remoteBanks = remoteDataSource.getBanks()
            val entities = remoteBanks.map { bankMapper.dtoToEntity(it) }
            localDataSource.insertBanks(entities)
        } catch (e: Exception) {
            // Handle error silently for background refresh
        }
    }
}
```

#### 2. **Independent Development and Testing**

Each module can be developed and tested in isolation:

```kotlin
// Local DataSource Testing
class BankLocalDataSourceTest {
    @Test
    fun `should return all banks from database`() = runTest {
        // Given
        val entities = listOf(
            BankEntity(id = "1", name = "Bank A", latitude = 22.0, longitude = 114.0),
            BankEntity(id = "2", name = "Bank B", latitude = 22.1, longitude = 114.1)
        )
        database.bankDao().insertAll(entities)
        
        // When
        val result = dataSource.getBanks()
        
        // Then
        assertEquals(entities, result)
    }
}

// Remote DataSource Testing
class BankRemoteDataSourceTest {
    @Test
    fun `should return banks from API`() = runTest {
        // Given
        val dtos = listOf(
            BankDto(id = "1", name = "Bank A", lat = 22.0, lng = 114.0),
            BankDto(id = "2", name = "Bank B", lat = 22.1, lng = 114.1)
        )
        mockWebServer.enqueue(MockResponse().setBody(Json.encodeToString(dtos)))
        
        // When
        val result = dataSource.getBanks()
        
        // Then
        assertEquals(dtos, result)
    }
}

// Repository Testing (with mocked data sources)
class BankRepositoryTest {
    @Mock private lateinit var localDataSource: BankLocalDataSource
    @Mock private lateinit var remoteDataSource: BankRemoteDataSource
    
    @Test
    fun `should return local data and refresh from remote`() = runTest {
        // Given
        val entities = listOf(mockBankEntity())
        val domains = listOf(mockBank())
        whenever(localDataSource.observeBanks()).thenReturn(flowOf(entities))
        whenever(bankMapper.entityToDomain(any())).thenReturn(domains.first())
        
        // When
        val result = repository.getBanks().first()
        
        // Then
        result.shouldBeSuccess()
        assertEquals(domains, result.getOrNull())
        verify(remoteDataSource).getBanks() // Verify background refresh
    }
}
```

#### 3. **Technology Independence**

Each module can use different technologies without affecting others:

```kotlin
// local-database module can use Room
// feature/bank/data/local-database/build.gradle.kts
dependencies {
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
}

// remote-network module can use Ktor
// feature/bank/data/remote-network/build.gradle.kts  
dependencies {
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.serialization.kotlinx.json)
}

// repo module only depends on abstractions
// feature/bank/data/repo/build.gradle.kts
dependencies {
    implementation(projects.feature.bank.data.localDatabase)
    implementation(projects.feature.bank.data.remoteNetwork)
    implementation(projects.feature.bank.domain) // For repository interface
}
```

#### 4. **Selective Compilation and Caching**

Changes in one data module don't affect others:

```
// Change in local-database module:
✅ Only local-database and repo modules recompile
❌ remote-network module unchanged (no recompilation needed)

// Change in remote-network module:  
✅ Only remote-network and repo modules recompile
❌ local-database module unchanged (no recompilation needed)
```

### Data Module Dependencies

```kotlin
// Clear dependency flow
feature/bank/data/repo {
    api(projects.feature.bank.domain) // Repository interface
    implementation(projects.feature.bank.data.localDatabase)
    implementation(projects.feature.bank.data.remoteNetwork)
    implementation(projects.core.network) // HTTP client
    implementation(projects.core.threading) // Dispatchers
}

feature/bank/data/local-database {
    implementation(projects.core.database) // Room setup
    // No dependency on remote or repo modules
}

feature/bank/data/remote-network {  
    implementation(projects.core.network) // HTTP client
    // No dependency on local or repo modules
}
```

## Testing Module Strategy

Testing modules provide shared testing utilities, fake implementations, and test infrastructure. The Findfin project includes several testing modules:

```
testing/
├── extension/              # Test extensions and utilities
├── instrument/             # Instrumented test helpers  
├── network/               # Network testing utilities
├── util/                  # General testing utilities
└── google-play-services-tasks/ # Google Play Services test helpers
```

### Why Separate Testing Modules?

#### 1. **Shared Testing Infrastructure**

```kotlin
// testing/extension/src/main/kotlin/CoroutineTestExtension.kt
class CoroutineTestExtension : BeforeEachCallback, AfterEachCallback {
    
    private lateinit var testDispatcher: TestDispatcher
    
    override fun beforeEach(context: ExtensionContext) {
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }
    
    override fun afterEach(context: ExtensionContext) {
        Dispatchers.resetMain()
    }
}

// testing/extension/src/main/kotlin/FlowTestExtensions.kt
suspend fun <T> Flow<T>.test(
    timeout: Duration = 1.seconds,
    action: suspend FlowTurbine<T>.() -> Unit
) {
    turbineScope {
        this@test.testIn(backgroundScope).action()
    }
}

// Usage in feature tests
class BankRepositoryTest {
    @JvmField
    @RegisterExtension
    val coroutineExtension = CoroutineTestExtension()
    
    @Test
    fun `should emit bank updates`() = runTest {
        repository.getBanks().test {
            awaitItem().shouldBeSuccess()
            expectNoEvents()
        }
    }
}
```

#### 2. **Fake Implementations for Testing**

```kotlin
// testing/util/src/main/kotlin/FakeBankRepository.kt
class FakeBankRepository : BankRepository {
    
    private val _banks = MutableStateFlow<List<Bank>>(emptyList())
    private var shouldReturnError = false
    
    fun setBanks(banks: List<Bank>) {
        _banks.value = banks
    }
    
    fun setShouldReturnError(shouldError: Boolean) {
        shouldReturnError = shouldError
    }
    
    override fun getBanks(): Flow<Result<List<Bank>>> {
        return _banks.map { banks ->
            if (shouldReturnError) {
                Result.failure(Exception("Test error"))
            } else {
                Result.success(banks)
            }
        }
    }
    
    override suspend fun refreshBanks(): Result<Unit> {
        return if (shouldReturnError) {
            Result.failure(Exception("Refresh failed"))
        } else {
            Result.success(Unit)
        }
    }
}

// Usage in UI tests
class BankListScreenTest {
    
    private val fakeBankRepository = FakeBankRepository()
    
    @Test
    fun `should display banks when data is available`() {
        // Given
        val testBanks = listOf(
            Bank(id = BankId("1"), name = "Test Bank", location = testLocation)
        )
        fakeBankRepository.setBanks(testBanks)
        
        // When
        composeTestRule.setContent {
            BankListScreen(repository = fakeBankRepository)
        }
        
        // Then
        composeTestRule.onNodeWithText("Test Bank").assertIsDisplayed()
    }
}
```

#### 3. **Network Testing Utilities**

```kotlin
// testing/network/src/main/kotlin/MockWebServerExtension.kt
class MockWebServerExtension : BeforeEachCallback, AfterEachCallback {
    
    lateinit var mockWebServer: MockWebServer
        private set
    
    override fun beforeEach(context: ExtensionContext) {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }
    
    override fun afterEach(context: ExtensionContext) {
        mockWebServer.shutdown()
    }
    
    fun enqueueResponse(
        code: Int = 200,
        body: String = "{}",
        headers: Map<String, String> = emptyMap()
    ) {
        val response = MockResponse()
            .setResponseCode(code)
            .setBody(body)
        
        headers.forEach { (key, value) ->
            response.addHeader(key, value)
        }
        
        mockWebServer.enqueue(response)
    }
}

// testing/network/src/main/kotlin/NetworkTestData.kt
object NetworkTestData {
    
    fun createBankDto(
        id: String = "test-id",
        name: String = "Test Bank",
        latitude: Double = 22.0,
        longitude: Double = 114.0
    ) = BankDto(
        id = id,
        name = name,
        lat = latitude,
        lng = longitude,
        services = listOf("ATM", "BRANCH"),
        address = "Test Address",
        phone = "+852 1234 5678"
    )
    
    fun createBanksResponse(banks: List<BankDto>) = Json.encodeToString(
        BanksResponse(
            status = "success",
            data = banks,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    )
}

// Usage in remote data source tests
class BankRemoteDataSourceTest {
    
    @JvmField
    @RegisterExtension
    val mockWebServerExtension = MockWebServerExtension()
    
    @Test
    fun `should parse banks response correctly`() = runTest {
        // Given
        val expectedBanks = listOf(
            NetworkTestData.createBankDto(id = "1", name = "Bank A"),
            NetworkTestData.createBankDto(id = "2", name = "Bank B")
        )
        val responseBody = NetworkTestData.createBanksResponse(expectedBanks)
        mockWebServerExtension.enqueueResponse(body = responseBody)
        
        // When
        val result = dataSource.getBanks()
        
        // Then
        assertEquals(expectedBanks, result)
    }
}
```

#### 4. **Instrumented Testing Support**

```kotlin
// testing/instrument/src/main/kotlin/TestApplication.kt
class TestApplication : Application(), HiltAndroidApp {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize test-specific configurations
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        }
    }
}

// testing/instrument/src/main/kotlin/HiltTestActivity.kt
@AndroidEntryPoint
class HiltTestActivity : ComponentActivity()

// testing/instrument/src/main/kotlin/TestDatabaseModule.kt
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDatabaseModule {
    
    @Provides
    @Singleton
    fun provideInMemoryDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}

// Usage in instrumented tests
@HiltAndroidTest
class BankDaoTest {
    
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    
    @Inject
    lateinit var database: AppDatabase
    
    private lateinit var bankDao: BankDao
    
    @Before
    fun setup() {
        hiltRule.inject()
        bankDao = database.bankDao()
    }
    
    @Test
    fun insertAndGetBanks() = runTest {
        // Given
        val banks = listOf(
            BankEntity(id = "1", name = "Bank A", latitude = 22.0, longitude = 114.0),
            BankEntity(id = "2", name = "Bank B", latitude = 22.1, longitude = 114.1)
        )
        
        // When
        bankDao.insertAll(banks)
        val result = bankDao.getAllBanks()
        
        // Then
        assertEquals(banks, result)
    }
}
```

### Testing Module Benefits

1. **Code Reuse**: Shared testing utilities across multiple modules
2. **Consistency**: Standardized testing patterns and helpers
3. **Maintainability**: Centralized test infrastructure updates
4. **Faster Test Writing**: Pre-built fakes and utilities
5. **Isolation**: Test dependencies don't pollute production code

## Module Dependency Guidelines

### Dependency Rules

```kotlin
// ✅ GOOD: Clear dependency hierarchy
app {
    implementation(projects.feature.auth.presentation)
    implementation(projects.feature.bank.presentation)
    implementation(projects.feature.home.presentation)
}

feature.bank.presentation {
    implementation(projects.feature.bank.domain)
    implementation(projects.core.design.theme)
    implementation(projects.core.navigation)
}

feature.bank.domain {
    // No dependencies on other features
    implementation(projects.core.threading)
}

feature.bank.data.repo {
    api(projects.feature.bank.domain)
    implementation(projects.feature.bank.data.localDatabase)
    implementation(projects.feature.bank.data.remoteNetwork)
}

// ❌ BAD: Circular dependencies
feature.auth.presentation {
    implementation(projects.feature.bank.presentation) // ❌ Feature depending on feature
}

feature.bank.domain {
    implementation(projects.feature.auth.domain) // ❌ Domain cross-dependencies
}
```

### Module Types and Dependencies

```
App Module
    ↓ (can depend on)
Feature Presentation Modules  
    ↓ (can depend on)
Feature Domain Modules
    ↓ (can depend on)
Core Modules
    ↓ (can depend on)
Library Modules
```

## Benefits and Trade-offs

### Benefits

#### 1. **Build Performance**
- **Parallel Compilation**: Multiple modules compile simultaneously
- **Incremental Builds**: Only changed modules recompile
- **Build Caching**: Unchanged modules use cached artifacts

#### 2. **Team Scalability**
- **Parallel Development**: Teams work on different modules independently
- **Code Ownership**: Clear module boundaries define team responsibilities
- **Reduced Merge Conflicts**: Less overlap in modified files

#### 3. **Code Quality**
- **Enforced Architecture**: Module boundaries prevent architectural violations
- **Testability**: Smaller, focused modules are easier to test
- **Reusability**: Well-defined modules can be reused across projects

#### 4. **Maintenance**
- **Focused Changes**: Bugs and features affect specific modules
- **Clear Dependencies**: Explicit module dependencies make impacts visible
- **Easier Refactoring**: Isolated modules can be refactored independently

### Trade-offs

#### 1. **Complexity**
- **Learning Curve**: Developers need to understand module structure
- **Setup Overhead**: Initial project setup is more complex
- **Dependency Management**: More complex dependency graph to manage

#### 2. **Over-modularization Risk**
- **Premature Optimization**: Creating modules before they're needed
- **Maintenance Overhead**: Too many small modules can be hard to manage
- **Interface Complexity**: APIs between modules add abstraction layers

## Best Practices

### 1. **Start Simple, Evolve Gradually**

```kotlin
// Phase 1: Start with feature modules
app/
feature-auth/
feature-bank/
core-shared/

// Phase 2: Split data layer when it grows
feature-bank/
├── presentation/
├── domain/
└── data/

// Phase 3: Split data layer further when needed  
feature-bank/
├── presentation/
├── domain/
└── data/
    ├── repo/
    ├── local-database/
    └── remote-network/
```

### 2. **Use Convention Plugins**

```kotlin
// build-logic/convention/src/main/kotlin/FeatureConventionPlugin.kt
class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("dagger.hilt.android.plugin")
            }
            
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("kapt", libs.findLibrary("hilt.compiler").get())
                add("testImplementation", projects.testing.util)
            }
        }
    }
}

// Usage in feature modules
// feature/bank/presentation/build.gradle.kts
plugins {
    id("findfin.android.feature")
    id("findfin.android.compose")
}
```

### 3. **Clear Module Naming**

```kotlin
// ✅ GOOD: Descriptive module names
core:design:theme
core:location:provider:api
feature:bank:data:local-database
feature:auth:presentation
testing:network

// ❌ BAD: Vague module names  
core:utils
feature:bank:stuff
common:helpers
```

### 4. **Document Module Purpose**

```kotlin
// feature/bank/presentation/README.md
# Bank Presentation Module

## Purpose
UI components and ViewModels for bank-related features including:
- Bank list and search
- Bank details and reviews  
- Bank location and directions

## Dependencies
- `feature:bank:domain` - Business logic and use cases
- `core:design:theme` - UI theming and components
- `core:navigation` - Navigation utilities

## Key Components
- `BankListScreen` - Main bank listing interface
- `BankDetailsScreen` - Individual bank information
- `BankListViewModel` - State management for bank list
```

## Common Anti-Patterns

### ❌ 1. **God Modules**

```kotlin
// BAD: One module doing everything
core:shared {
    // Database setup
    // Network configuration  
    // UI components
    // Business logic
    // Testing utilities
    // etc.
}

// GOOD: Focused modules
core:database
core:network
core:design:ui-foundation
feature:bank:domain
testing:util
```

### ❌ 2. **Circular Dependencies**

```kotlin
// BAD: Modules depending on each other
feature:auth:presentation {
    implementation(projects.feature.bank.presentation)
}

feature:bank:presentation {
    implementation(projects.feature.auth.presentation) // ❌ Circular dependency
}

// GOOD: Extract shared dependencies
feature:auth:presentation {
    implementation(projects.core.navigation)
}

feature:bank:presentation {
    implementation(projects.core.navigation)
}
```

### ❌ 3. **Leaky Abstractions**

```kotlin
// BAD: Exposing implementation details through API
// core/network/api/src/main/kotlin/NetworkProvider.kt
interface NetworkProvider {
    fun getHttpClient(): OkHttpClient // ❌ Exposing OkHttp specifics
    fun getRetrofitInstance(): Retrofit // ❌ Exposing Retrofit specifics
}

// GOOD: Abstract, technology-agnostic API
interface NetworkProvider {
    suspend fun <T> makeRequest(request: NetworkRequest<T>): Result<T>
    fun createWebSocketConnection(url: String): WebSocketConnection
}
```

### ❌ 4. **Premature Modularization**

```kotlin
// BAD: Over-modularizing too early
feature:bank:presentation:ui:components:button
feature:bank:presentation:ui:components:card  
feature:bank:presentation:ui:components:dialog

// GOOD: Start with broader modules, split when needed
feature:bank:presentation
// Split later when UI components become substantial
```

## Conclusion

Modularization in the Findfin project serves multiple purposes:

### **API/Implementation Separation**
- **Flexibility**: Swap implementations without affecting consumers
- **Testability**: Mock APIs instead of complex implementations  
- **Parallel Development**: Teams can work on contracts and implementations independently
- **Build Performance**: Implementation changes don't trigger consumer recompilation

### **Data Layer Modularization**
- **Single Responsibility**: Each module has one clear purpose (local, remote, coordination)
- **Technology Independence**: Use different technologies in each layer
- **Testing Isolation**: Test each data source independently
- **Selective Compilation**: Changes only affect related modules

### **Testing Module Strategy**
- **Shared Infrastructure**: Common testing utilities across modules
- **Consistent Patterns**: Standardized testing approaches
- **Fake Implementations**: Reusable test doubles
- **Faster Development**: Pre-built testing tools and helpers

The key to successful modularization is finding the right balance between **granularity** and **simplicity**. Start with broader modules and split them as the codebase grows and specific needs emerge. Always prioritize **clear boundaries**, **explicit dependencies**, and **focused responsibilities** over premature optimization. 