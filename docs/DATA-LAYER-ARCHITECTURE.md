# Data Layer Architecture

## Table of Contents
- [Overview](#overview)
- [Data Layer Components](#data-layer-components)
- [Datasource Responsibilities](#datasource-responsibilities)
- [Repository Responsibilities](#repository-responsibilities)
- [Data Flow Architecture](#data-flow-architecture)
- [Implementation Patterns](#implementation-patterns)
- [Error Handling](#error-handling)
- [Testing Strategies](#testing-strategies)
- [Best Practices](#best-practices)
- [Common Anti-Patterns](#common-anti-patterns)

## Overview

The data layer is responsible for managing application data and business logic. It acts as a bridge between the domain layer and external data sources, providing a clean and consistent API for data access while handling caching, synchronization, and error management.

### Key Principles
- **Single Source of Truth**: Repository provides unified data access
- **Separation of Concerns**: Clear separation between local and remote data sources
- **Reactive Data**: Use Flow for reactive data streams
- **Offline-First**: Local data as primary source with network synchronization
- **Error Handling**: Consistent error propagation and recovery

### Architecture Overview
```
┌─────────────────┐
│   Domain Layer  │
│   (Use Cases)   │
└─────────┬───────┘
          │
┌─────────▼───────┐
│   Repository    │ ◄── Single source of truth
│  (Coordination) │
└─────────┬───────┘
          │
     ┌────▼────┐
     │         │
┌────▼───┐ ┌──▼────┐
│ Local  │ │Remote │
│DataSrc │ │DataSrc│
└────────┘ └───────┘
```

## Data Layer Components

### 1. Repository
- **Role**: Coordination and business logic
- **Responsibility**: Single source of truth for domain data
- **Dependencies**: Multiple datasources, mappers

### 2. Local DataSource
- **Role**: Local data management
- **Responsibility**: Database, cache, and preferences access
- **Dependencies**: Room, DataStore, file system

### 3. Remote DataSource  
- **Role**: Network data access
- **Responsibility**: API calls and network communication
- **Dependencies**: Ktor, HTTP clients

### 4. Data Models
- **Entity**: Database models (Room entities)
- **DTO**: Data Transfer Objects (network models)
- **Domain**: Business logic models

## Datasource Responsibilities

### Local DataSource

**Primary Responsibilities:**
- ✅ Database operations (CRUD)
- ✅ Local caching management
- ✅ Offline data storage
- ✅ Data persistence
- ✅ Query optimization

**What Local DataSource Should Do:**
```kotlin
interface UserLocalDataSource {
    suspend fun getUsers(): List<UserEntity>
    suspend fun getUserById(id: String): UserEntity?
    suspend fun insertUsers(users: List<UserEntity>)
    suspend fun updateUser(user: UserEntity)
    suspend fun deleteUser(id: String)
    suspend fun clearUsers()
    fun observeUsers(): Flow<List<UserEntity>>
}

@Singleton
class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {
    
    override suspend fun getUsers(): List<UserEntity> = 
        userDao.getAllUsers()
    
    override fun observeUsers(): Flow<List<UserEntity>> = 
        userDao.observeAllUsers()
    
    override suspend fun insertUsers(users: List<UserEntity>) = 
        userDao.insertAll(users)
}
```

**What Local DataSource Should NOT Do:**
- ❌ Business logic decisions
- ❌ Data validation beyond database constraints
- ❌ Network calls or external API access
- ❌ Complex data transformations
- ❌ Cross-entity relationship management

### Remote DataSource

**Primary Responsibilities:**
- ✅ Network API calls
- ✅ HTTP request/response handling
- ✅ Authentication token management
- ✅ Network error handling
- ✅ Data serialization/deserialization

**What Remote DataSource Should Do:**
```kotlin
interface UserRemoteDataSource {
    suspend fun getUsers(): List<UserDto>
    suspend fun getUserById(id: String): UserDto
    suspend fun createUser(userDto: UserDto): UserDto
    suspend fun updateUser(id: String, userDto: UserDto): UserDto
    suspend fun deleteUser(id: String)
}

@Singleton
class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : UserRemoteDataSource {
    
    override suspend fun getUsers(): List<UserDto> = 
        userApi.getUsers()
    
    override suspend fun getUserById(id: String): UserDto = 
        userApi.getUser(id)
    
    override suspend fun createUser(userDto: UserDto): UserDto = 
        userApi.createUser(userDto)
}
```

**What Remote DataSource Should NOT Do:**
- ❌ Local data storage or caching
- ❌ Business logic implementation
- ❌ Data model transformations
- ❌ Offline handling logic
- ❌ Complex retry mechanisms (use repository for this)

## Repository Responsibilities

### Primary Responsibilities

**1. Data Coordination**
- Coordinate between local and remote data sources
- Implement caching strategies
- Handle data synchronization
- Manage data consistency

**2. Business Logic**
- Implement domain-specific business rules
- Data validation and transformation
- Complex data operations
- Cross-entity relationships

**3. Error Handling**
- Network error recovery
- Offline fallback strategies
- Error propagation to domain layer
- Retry mechanisms

**4. Data Mapping**
- Convert between different data models
- Entity ↔ Domain model transformation
- DTO ↔ Domain model transformation

### Repository Implementation Example

```kotlin
interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun getUserById(id: String): Result<User>
    suspend fun refreshUsers(): Result<Unit>
    suspend fun createUser(user: User): Result<User>
    suspend fun updateUser(user: User): Result<User>
    suspend fun deleteUser(id: String): Result<Unit>
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
    private val userMapper: UserMapper,
    private val networkConnectivity: NetworkConnectivity,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override fun getUsers(): Flow<List<User>> = 
        localDataSource.observeUsers()
            .map { entities -> 
                entities.map { userMapper.entityToDomain(it) }
            }
            .flowOn(ioDispatcher)

    override suspend fun refreshUsers(): Result<Unit> = withContext(ioDispatcher) {
        try {
            if (!networkConnectivity.isConnected()) {
                return@withContext Result.failure(NoNetworkException())
            }
            
            val remoteUsers = remoteDataSource.getUsers()
            val entities = remoteUsers.map { userMapper.dtoToEntity(it) }
            localDataSource.insertUsers(entities)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(id: String): Result<User> = withContext(ioDispatcher) {
        try {
            // Try local first
            val localUser = localDataSource.getUserById(id)
            if (localUser != null) {
                return@withContext Result.success(userMapper.entityToDomain(localUser))
            }
            
            // Fallback to remote if available
            if (networkConnectivity.isConnected()) {
                val remoteUser = remoteDataSource.getUserById(id)
                val entity = userMapper.dtoToEntity(remoteUser)
                localDataSource.insertUsers(listOf(entity))
                return@withContext Result.success(userMapper.entityToDomain(entity))
            }
            
            Result.failure(UserNotFoundException(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createUser(user: User): Result<User> = withContext(ioDispatcher) {
        try {
            // Validate business rules
            validateUser(user)
            
            val dto = userMapper.domainToDto(user)
            val createdDto = remoteDataSource.createUser(dto)
            val entity = userMapper.dtoToEntity(createdDto)
            
            localDataSource.insertUsers(listOf(entity))
            
            Result.success(userMapper.entityToDomain(entity))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun validateUser(user: User) {
        require(user.email.isNotBlank()) { "Email cannot be empty" }
        require(user.name.isNotBlank()) { "Name cannot be empty" }
        // Additional business validation
    }
}
```

### What Repository Should Do:
- ✅ Coordinate multiple data sources
- ✅ Implement caching strategies
- ✅ Handle offline scenarios
- ✅ Provide reactive data streams
- ✅ Map between data models
- ✅ Implement business logic
- ✅ Handle errors gracefully
- ✅ Manage data synchronization

### What Repository Should NOT Do:
- ❌ Direct database operations (delegate to local datasource)
- ❌ Direct network calls (delegate to remote datasource)
- ❌ UI-related logic
- ❌ Platform-specific code
- ❌ Presentation formatting

## Data Flow Architecture

### Read Operations (Offline-First)
```
1. Repository receives request
2. Check local datasource first
3. Return local data if available
4. If not available and network connected:
   - Fetch from remote datasource
   - Cache in local datasource
   - Return data
5. Handle errors appropriately
```

### Write Operations
```
1. Repository receives write request
2. Validate data (business rules)
3. Send to remote datasource
4. On success: update local datasource
5. On failure: handle error (queue for retry?)
6. Return result
```

### Data Synchronization
```kotlin
class SyncManager @Inject constructor(
    private val repositories: Set<@JvmSuppressWildcards SyncableRepository>
) {
    suspend fun syncAll(): Result<Unit> = supervisorScope {
        repositories.map { repository ->
            async { repository.sync() }
        }.awaitAll()
        
        Result.success(Unit)
    }
}

interface SyncableRepository {
    suspend fun sync(): Result<Unit>
}
```

## Implementation Patterns

### 1. Repository Factory Pattern
```kotlin
interface RepositoryFactory {
    fun createUserRepository(): UserRepository
    fun createProductRepository(): ProductRepository
}

@Singleton
class RepositoryFactoryImpl @Inject constructor(
    private val localDataSources: LocalDataSourceProvider,
    private val remoteDataSources: RemoteDataSourceProvider,
    private val mappers: MapperProvider
) : RepositoryFactory {
    
    override fun createUserRepository(): UserRepository = 
        UserRepositoryImpl(
            localDataSources.userLocalDataSource(),
            remoteDataSources.userRemoteDataSource(),
            mappers.userMapper()
        )
}
```

### 2. Generic Repository Pattern
```kotlin
abstract class BaseRepository<Entity, Dto, Domain>(
    protected val localDataSource: LocalDataSource<Entity>,
    protected val remoteDataSource: RemoteDataSource<Dto>,
    protected val mapper: DataMapper<Entity, Dto, Domain>
) {
    
    protected suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Result<T> = try {
        Result.success(apiCall())
    } catch (e: Exception) {
        Result.failure(e)
    }
    
    protected fun <T> safeLocalCall(
        localCall: () -> T
    ): Result<T> = try {
        Result.success(localCall())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

### 3. Caching Strategies
```kotlin
class CachedRepository @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
    private val cachePolicy: CachePolicy
) {
    
    suspend fun getUsers(forceRefresh: Boolean = false): Result<List<User>> {
        if (!forceRefresh && cachePolicy.isValid()) {
            val cachedUsers = localDataSource.getUsers()
            if (cachedUsers.isNotEmpty()) {
                return Result.success(cachedUsers.map { it.toDomain() })
            }
        }
        
        return refreshFromRemote()
    }
    
    private suspend fun refreshFromRemote(): Result<List<User>> {
        return try {
            val remoteUsers = remoteDataSource.getUsers()
            localDataSource.clearUsers()
            localDataSource.insertUsers(remoteUsers.map { it.toEntity() })
            cachePolicy.updateLastRefresh()
            
            Result.success(remoteUsers.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

## Thread Handling in Data Layer

### Core Threading Principles

The data layer should handle threading transparently, ensuring all operations are non-blocking and properly scoped. Key principles:

1. **Use appropriate dispatchers**: IO for network/database, Default for CPU work
2. **Repository owns threading**: DataSources should be dispatcher-agnostic
4. **Inject dispatchers**: For testability and flexibility
5. **Handle cancellation**: Respect coroutine cancellation throughout the chain

### 1. DataSource Threading Patterns

#### Local DataSource - Let Repository Handle Threading

```kotlin
@Singleton
class BankLocalDataSourceImpl @Inject constructor(
    private val bankDao: BankDao
) : BankLocalDataSource {
    
    // ✅ GOOD: No explicit threading - let repository handle it
    override suspend fun getBanks(): List<BankEntity> = bankDao.getAllBanks()
    
    override fun observeBanks(): Flow<List<BankEntity>> = bankDao.observeAllBanks()
    
    override suspend fun insertBanks(banks: List<BankEntity>) = bankDao.insertAll(banks)
    
    override suspend fun getBankById(id: String): BankEntity? = bankDao.getBankById(id)
    
    // ✅ GOOD: Room handles threading automatically for @Query operations
    override suspend fun getBanksNearLocation(
        latitude: Double,
        longitude: Double,
        radiusKm: Double
    ): List<BankEntity> = bankDao.getBanksNearLocation(latitude, longitude, radiusKm)
}
```

#### Remote DataSource - Focus on Network Operations

```kotlin
@Singleton
class BankRemoteDataSourceImpl @Inject constructor(
    private val bankApi: BankApi,
    private val authTokenProvider: AuthTokenProvider
) : BankRemoteDataSource {
    
    // ✅ GOOD: Pure network operations, let repository handle threading
    override suspend fun getAllBanks(): List<BankDto> = bankApi.getBanks()
    
    override suspend fun getBankById(id: String): BankDto = bankApi.getBank(id)
    
    override suspend fun searchBanks(query: String): List<BankDto> = 
        bankApi.searchBanks(query)
    
    // ✅ GOOD: Handle authentication but not threading
    override suspend fun createBank(bank: BankDto): BankDto {
        val token = authTokenProvider.getValidToken()
        return bankApi.createBank(bank, "Bearer $token")
    }
}
```

### 2. Flow Composition and Threading

#### Combining Multiple Data Sources

```kotlin
@Singleton
class BankRepositoryImpl @Inject constructor(
    private val localDataSource: BankLocalDataSource,
    private val remoteDataSource: BankRemoteDataSource,
    private val queueDataSource: QueueRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : BankRepository {
    
    override fun getBanksWithLiveQueueInfo(): Flow<List<BankWithQueueInfo>> {
        return combine(
            localDataSource.observeBanks(),
            queueDataSource.observeAllQueueInfo()
        ) { banks, queueInfoList ->
            // CPU-intensive combination logic
            banks.map { bank ->
                val queueInfo = queueInfoList.find { it.bankId == bank.id }
                BankWithQueueInfo(
                    bank = bankMapper.entityToDomain(bank),
                    queueInfo = queueInfo?.let { queueMapper.dtoToDomain(it) },
                    estimatedWaitTime = calculateWaitTime(bank, queueInfo)
                )
            }
        }
        .flowOn(defaultDispatcher) // CPU work on Default dispatcher
    }
    
    override fun getBanksNearLocationWithSync(
        location: Location,
        radiusKm: Double
    ): Flow<Result<List<Bank>>> {
        return localDataSource.getBanksNearLocation(
            location.latitude,
            location.longitude,
            radiusKm
        )
        .map { entities ->
            entities.map { bankMapper.entityToDomain(it) }
        }
        .map { Result.success(it) }
        .onStart {
            // Trigger background sync (fire-and-forget)
            launch {
                syncBanksInBackground(location, radiusKm)
            }
        }
        .catch { exception ->
            emit(Result.failure(exception))
        }
        .flowOn(ioDispatcher) // Database and network operations on IO
    }
    
    private suspend fun syncBanksInBackground(location: Location, radiusKm: Double) {
        try {
            val remoteBanks = remoteDataSource.getBanksNearLocation(
                location.latitude,
                location.longitude,
                radiusKm
            )
            val entities = remoteBanks.map { bankMapper.dtoToEntity(it) }
            localDataSource.insertBanks(entities)
        } catch (e: Exception) {
            // Log error but don't propagate (background operation)
            logger.e("Background sync failed", e)
        }
    }
}
```

### 3. Advanced Threading Patterns

#### Batch Processing with Concurrency Control

```kotlin
@Singleton
class BankRepositoryImpl @Inject constructor(
    private val localDataSource: BankLocalDataSource,
    private val remoteDataSource: BankRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BankRepository {
    
    override suspend fun syncBanksBatch(bankIds: List<BankId>): Result<Unit> = withContext(ioDispatcher) {
        try {
            // Process in chunks to avoid overwhelming the server
            bankIds.chunked(BATCH_SIZE).forEach { chunk ->
                chunk.map { bankId ->
                    async {
                        syncSingleBank(bankId)
                    }
                }.awaitAll()
                
                // Small delay between batches to be respectful to the server
                delay(BATCH_DELAY_MS)
            }
            
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun syncSingleBank(bankId: BankId): Unit {
        val remoteBank = remoteDataSource.getBankById(bankId.value)
        val entity = bankMapper.dtoToEntity(remoteBank)
        localDataSource.insertBanks(listOf(entity))
    }
    
    companion object {
        private const val BATCH_SIZE = 5
        private const val BATCH_DELAY_MS = 100L
    }
}
```

#### Rate-Limited Operations

```kotlin
@Singleton
class BankRepositoryImpl @Inject constructor(
    private val remoteDataSource: BankRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BankRepository {
    
    private val rateLimiter = Semaphore(MAX_CONCURRENT_REQUESTS)
    
    override suspend fun searchBanksWithRateLimit(query: String): Result<List<Bank>> = withContext(ioDispatcher) {
        rateLimiter.withPermit {
            try {
                val results = remoteDataSource.searchBanks(query)
                Result.success(results.map { bankMapper.dtoToDomain(it) })
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    companion object {
        private const val MAX_CONCURRENT_REQUESTS = 3
    }
}

// Extension function for cleaner syntax
suspend fun <T> Semaphore.withPermit(action: suspend () -> T): T {
    acquire()
    try {
        return action()
    } finally {
        release()
    }
}
```

### 4. Testing Threading in Data Layer

#### Repository Testing with Test Dispatchers

```kotlin
@ExtendWith(MockitoExtension::class)
class BankRepositoryImplTest {

    @Mock private lateinit var localDataSource: BankLocalDataSource
    @Mock private lateinit var remoteDataSource: BankRemoteDataSource
    @Mock private lateinit var bankMapper: BankMapper
    
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: BankRepositoryImpl
    
    @BeforeEach
    fun setup() {
        repository = BankRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource,
            bankMapper = bankMapper,
            ioDispatcher = testDispatcher // Inject test dispatcher
        )
    }
    
    @Test
    fun `getBanksNearLocation should emit local data on IO dispatcher`() = runTest {
        // Given
        val location = TestData.sampleLocation
        val entities = TestData.sampleBankEntities
        val domains = TestData.sampleBanks
        
        given(localDataSource.getBanksNearLocation(any(), any(), any()))
            .willReturn(flowOf(entities))
        given(bankMapper.entityToDomain(any()))
            .willReturn(domains.first())
        
        // When
        val result = repository.getBanksNearLocation(location, 10.0).first()
        
        // Then
        result.shouldBeSuccess()
        result.getOrNull() shouldBe domains
        
        // Verify operations ran on correct dispatcher
        verify(localDataSource).getBanksNearLocation(
            location.latitude,
            location.longitude,
            10.0
        )
    }
    
    @Test
    fun `refreshBanks should handle concurrent operations`() = runTest {
        // Given
        val remoteBanks = TestData.sampleBankDtos
        val entities = TestData.sampleBankEntities
        
        given(remoteDataSource.getAllBanks()).willReturn(remoteBanks)
        given(bankMapper.dtoToEntity(any())).willReturn(entities.first())
        
        // When
        val result = repository.refreshBanks()
        
        // Then
        result.shouldBeSuccess()
        verify(localDataSource).clearAllBanks()
        verify(localDataSource).insertBanks(any())
    }
}
```

#### Flow Testing with Threading

```kotlin
@Test
fun `getBanksWithLiveQueueInfo should combine flows on correct dispatcher`() = runTest {
    // Given
    val bankEntities = TestData.sampleBankEntities
    val queueInfos = TestData.sampleQueueInfos
    
    given(localDataSource.observeBanks())
        .willReturn(flowOf(bankEntities))
    given(queueDataSource.observeAllQueueInfo())
        .willReturn(flowOf(queueInfos))
    
    // When
    val result = repository.getBanksWithLiveQueueInfo().first()
    
    // Then
    result shouldHaveSize bankEntities.size
    // Verify the combination logic worked correctly
}
```

## Error Handling

### Repository Error Handling
```kotlin
sealed class DataError : Exception() {
    object NetworkError : DataError()
    object CacheError : DataError()
    data class ValidationError(override val message: String) : DataError()
    data class NotFoundError(val id: String) : DataError()
}

suspend fun <T> Repository.handleDataOperation(
    operation: suspend () -> T
): Result<T> = try {
    Result.success(operation())
} catch (e: IOException) {
    Result.failure(DataError.NetworkError)
} catch (e: SQLException) {
    Result.failure(DataError.CacheError)
} catch (e: IllegalArgumentException) {
    Result.failure(DataError.ValidationError(e.message ?: "Validation failed"))
} catch (e: Exception) {
    Result.failure(e)
}
```

### Datasource Error Boundaries
```kotlin
// Local DataSource - Don't propagate exceptions
class UserLocalDataSourceImpl @Inject constructor(
    private val userDao: UserDao
) : UserLocalDataSource {
    
    override suspend fun getUsers(): List<UserEntity> = try {
        userDao.getAllUsers()
    } catch (e: SQLException) {
        emptyList() // Graceful degradation
    }
}

// Remote DataSource - Let exceptions bubble up
class UserRemoteDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : UserRemoteDataSource {
    
    override suspend fun getUsers(): List<UserDto> = 
        userApi.getUsers() // Let network exceptions propagate
}
```

## Testing Strategies

### Repository Testing
```kotlin
class UserRepositoryTest {
    @Mock private lateinit var localDataSource: UserLocalDataSource
    @Mock private lateinit var remoteDataSource: UserRemoteDataSource
    @Mock private lateinit var userMapper: UserMapper
    
    private lateinit var repository: UserRepository
    
    @Before
    fun setup() {
        repository = UserRepositoryImpl(
            localDataSource,
            remoteDataSource,
            userMapper,
            TestDispatchers.IO
        )
    }
    
    @Test
    fun `getUsers returns local data when available`() = runTest {
        // Given
        val entities = listOf(mockUserEntity())
        val domains = listOf(mockUser())
        whenever(localDataSource.observeUsers()).thenReturn(flowOf(entities))
        whenever(userMapper.entityToDomain(any())).thenReturn(domains.first())
        
        // When
        val result = repository.getUsers().first()
        
        // Then
        assertEquals(domains, result)
    }
}
```

### DataSource Testing
```kotlin
class UserLocalDataSourceTest {
    @Mock private lateinit var userDao: UserDao
    private lateinit var dataSource: UserLocalDataSource
    
    @Test
    fun `insertUsers calls dao insertAll`() = runTest {
        // Given
        val users = listOf(mockUserEntity())
        dataSource = UserLocalDataSourceImpl(userDao)
        
        // When
        dataSource.insertUsers(users)
        
        // Then
        verify(userDao).insertAll(users)
    }
}
```

## Best Practices

### 1. Use Appropriate Data Types
```kotlin
// ✅ Good - Use Flow for reactive data
fun getUsers(): Flow<List<User>>

// ✅ Good - Use Result for operations that can fail
suspend fun createUser(user: User): Result<User>

// ❌ Avoid - Exposing LiveData from repository
fun getUsers(): LiveData<List<User>>
```

### 2. Implement Proper Caching
```kotlin
// ✅ Good - Cache with expiration
class CachePolicy(
    private val maxAgeMillis: Long = TimeUnit.HOURS.toMillis(1)
) {
    private var lastRefresh = 0L
    
    fun isValid(): Boolean = 
        System.currentTimeMillis() - lastRefresh < maxAgeMillis
}
```

### 3. Handle Offline Scenarios
```kotlin
// ✅ Good - Offline-first approach
override fun getUsers(): Flow<List<User>> = 
    localDataSource.observeUsers()
        .map { entities -> entities.map { it.toDomain() } }
        .onStart { 
            if (networkConnectivity.isConnected()) {
                refreshFromRemote()
            }
        }
```

### 4. Use Dependency Injection
```kotlin
// ✅ Good - Constructor injection
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserRepository
```

## Common Anti-Patterns

### ❌ Repository Doing Direct Database Operations
```kotlin
// Bad - Repository accessing database directly
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao // Should be through datasource
) {
    override suspend fun getUsers() = userDao.getAllUsers() // Wrong!
}
```

### ❌ DataSource Implementing Business Logic
```kotlin
// Bad - DataSource with business logic
class UserLocalDataSourceImpl {
    suspend fun getActiveUsers(): List<UserEntity> {
        return userDao.getAllUsers()
            .filter { it.isActive && it.lastLoginDays < 30 } // Business logic!
    }
}
```

### ❌ Mixed Responsibilities
```kotlin
// Bad - Repository doing UI formatting
class UserRepositoryImpl {
    suspend fun getFormattedUserName(id: String): String {
        val user = getUserById(id)
        return "${user.firstName} ${user.lastName}" // UI concern!
    }
}
```

### ❌ Synchronous Operations
```kotlin
// Bad - Blocking operations
class UserRepositoryImpl {
    fun getUsers(): List<User> = runBlocking { // Don't block!
        remoteDataSource.getUsers()
    }
}
```

## Conclusion

The data layer is crucial for:
- **Data Consistency**: Single source of truth through repositories
- **Offline Support**: Local-first approach with network synchronization
- **Testability**: Clear separation of concerns enables easy testing
- **Maintainability**: Well-defined responsibilities reduce complexity
- **Performance**: Efficient caching and reactive data streams

### Remember:
- **DataSources**: Focus on data access, no business logic
- **Repositories**: Coordinate datasources, implement business logic
- **Always**: Use coroutines, handle errors gracefully, test thoroughly
- **Never**: Mix UI concerns, block threads, or ignore offline scenarios

Following these patterns ensures a robust, maintainable, and scalable data layer that serves as a solid foundation for your application. 