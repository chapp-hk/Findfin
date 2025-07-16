# Domain Layer Architecture

## Overview

The domain layer is the innermost layer of clean architecture, containing the business logic and rules that are independent of any external concerns like UI, database, or network. It defines what the application does, not how it does it.

## Architecture Components

```
┌─────────────────────────────────────────┐
│              Presentation               │
│         (ViewModels, Compose UI)        │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│               Domain                    │
│        ┌─────────────────────┐         │
│        │     Use Cases       │         │
│        └─────────────────────┘         │
│        ┌─────────────────────┐         │
│        │   Domain Models     │         │
│        └─────────────────────┘         │
│        ┌─────────────────────┐         │
│        │   Repository APIs   │         │
│        └─────────────────────┘         │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│                Data                     │
│      (Repositories, DataSources)       │
└─────────────────────────────────────────┘
```

## Domain Components

### 1. Domain Models (Entities)

**Purpose**: Represent core business objects and their invariants

**Characteristics**:
- Pure Kotlin data classes or sealed classes
- No Android/framework dependencies
- Contain business rules and validation logic
- Immutable where possible

**Example**:
```kotlin
// feature/bank/domain/src/main/kotlin/org/mycompany/findfin/bank/domain/model/Bank.kt
data class Bank(
    val id: BankId,
    val name: String,
    val location: Location,
    val services: List<BankService>,
    val operatingHours: OperatingHours
) {
    init {
        require(name.isNotBlank()) { "Bank name cannot be blank" }
        require(services.isNotEmpty()) { "Bank must provide at least one service" }
    }
    
    fun isOpen(currentTime: LocalTime): Boolean {
        return operatingHours.isOpenAt(currentTime)
    }
    
    fun supportsService(service: BankService): Boolean {
        return services.contains(service)
    }
}

@JvmInline
value class BankId(val value: String) {
    init {
        require(value.isNotBlank()) { "Bank ID cannot be blank" }
    }
}
```

### 2. Repository Interfaces

**Purpose**: Define contracts for data access without specifying implementation

**Characteristics**:
- Interfaces only (no implementation)
- Return domain models
- Use Flow for reactive data streams
- Abstract away data source details

**Example**:
```kotlin
// feature/bank/domain/src/main/kotlin/org/mycompany/findfin/bank/domain/repository/BankRepository.kt
interface BankRepository {
    fun getBanksNearLocation(
        location: Location,
        radiusKm: Double
    ): Flow<Result<List<Bank>>>
    
    fun getBankById(id: BankId): Flow<Result<Bank?>>
    
    fun searchBanks(
        query: String,
        location: Location? = null
    ): Flow<Result<List<Bank>>>
    
    suspend fun refreshBanks(): Result<Unit>
    
    fun getFavoriteBanks(): Flow<List<Bank>>
    
    suspend fun toggleFavorite(bankId: BankId): Result<Unit>
}
```

### 3. Use Cases

**Purpose**: Encapsulate specific business operations and orchestrate domain logic

## Use Case Responsibilities

### Core Responsibilities

#### ✅ What Use Cases SHOULD Do

1. **Business Logic Orchestration**
   ```kotlin
   class FindNearbyBanksUseCase @Inject constructor(
       private val bankRepository: BankRepository,
       private val locationRepository: LocationRepository,
       private val userPreferencesRepository: UserPreferencesRepository
   ) {
       suspend operator fun invoke(
           radiusKm: Double? = null
       ): Flow<Result<List<Bank>>> = flow {
           // 1. Get user's current location
           val location = locationRepository.getCurrentLocation().getOrElse {
               emit(Result.failure(LocationUnavailableException()))
               return@flow
           }
           
           // 2. Get user's preferred radius or use default
           val searchRadius = radiusKm 
               ?: userPreferencesRepository.getPreferredSearchRadius().first()
           
           // 3. Apply business rules
           val validatedRadius = validateSearchRadius(searchRadius)
           
           // 4. Fetch and filter banks
           bankRepository.getBanksNearLocation(location, validatedRadius)
               .map { result ->
                   result.map { banks ->
                       banks.filter { bank ->
                           // Apply business logic: only show open banks during business hours
                           bank.isOpen(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
                       }
                   }
               }
               .collect { emit(it) }
       }
       
       private fun validateSearchRadius(radius: Double): Double {
           return when {
               radius <= 0 -> throw InvalidSearchRadiusException("Radius must be positive")
               radius > MAX_SEARCH_RADIUS_KM -> MAX_SEARCH_RADIUS_KM
               else -> radius
           }
       }
       
       companion object {
           private const val MAX_SEARCH_RADIUS_KM = 50.0
       }
   }
   ```

2. **Data Transformation & Validation**
   ```kotlin
   class ValidateBankSearchQueryUseCase @Inject constructor() {
       operator fun invoke(query: String): Result<String> {
           return when {
               query.isBlank() -> Result.failure(
                   ValidationException("Search query cannot be empty")
               )
               query.length < MIN_QUERY_LENGTH -> Result.failure(
                   ValidationException("Search query must be at least $MIN_QUERY_LENGTH characters")
               )
               query.length > MAX_QUERY_LENGTH -> Result.failure(
                   ValidationException("Search query cannot exceed $MAX_QUERY_LENGTH characters")
               )
               else -> Result.success(query.trim())
           }
       }
       
       companion object {
           private const val MIN_QUERY_LENGTH = 2
           private const val MAX_QUERY_LENGTH = 100
       }
   }
   ```

3. **Cross-Repository Coordination**
   ```kotlin
   class ToggleBankFavoriteUseCase @Inject constructor(
       private val bankRepository: BankRepository,
       private val userRepository: UserRepository,
       private val analyticsRepository: AnalyticsRepository
   ) {
       suspend operator fun invoke(bankId: BankId): Result<Boolean> {
           return try {
               // 1. Check if user is authenticated
               val user = userRepository.getCurrentUser().first()
                   ?: return Result.failure(UserNotAuthenticatedException())
               
               // 2. Toggle favorite status
               val result = bankRepository.toggleFavorite(bankId)
               
               // 3. Track analytics event
               result.onSuccess { isFavorite ->
                   analyticsRepository.trackEvent(
                       if (isFavorite) "bank_favorited" else "bank_unfavorited",
                       mapOf("bank_id" to bankId.value, "user_id" to user.id.value)
                   )
               }
               
               result
           } catch (e: Exception) {
               Result.failure(e)
           }
       }
   }
   ```

4. **Business Rule Enforcement**
   ```kotlin
   class CalculateBankRatingUseCase @Inject constructor() {
       operator fun invoke(bank: Bank, userReviews: List<UserReview>): BankRating {
           require(userReviews.all { it.bankId == bank.id }) {
               "All reviews must be for the specified bank"
           }
           
           val averageRating = if (userReviews.isEmpty()) {
               DEFAULT_RATING
           } else {
               userReviews.map { it.rating }.average()
           }
           
           val adjustedRating = applyBusinessRules(bank, averageRating, userReviews)
           
           return BankRating(
               bankId = bank.id,
               score = adjustedRating,
               reviewCount = userReviews.size,
               lastUpdated = Clock.System.now()
           )
       }
       
       private fun applyBusinessRules(
           bank: Bank, 
           baseRating: Double, 
           reviews: List<UserReview>
       ): Double {
           var adjustedRating = baseRating
           
           // Business rule: New banks get a slight boost
           if (bank.establishedDate.isAfter(Clock.System.now().minus(1.years))) {
               adjustedRating += NEW_BANK_BONUS
           }
           
           // Business rule: Penalize banks with accessibility issues
           if (reviews.any { it.hasAccessibilityIssues }) {
               adjustedRating -= ACCESSIBILITY_PENALTY
           }
           
           return adjustedRating.coerceIn(MIN_RATING, MAX_RATING)
       }
       
       companion object {
           private const val DEFAULT_RATING = 3.0
           private const val NEW_BANK_BONUS = 0.2
           private const val ACCESSIBILITY_PENALTY = 0.5
           private const val MIN_RATING = 1.0
           private const val MAX_RATING = 5.0
       }
   }
   ```

#### ❌ What Use Cases SHOULD NOT Do

1. **UI Logic**
   ```kotlin
   // ❌ BAD: Use case should not know about UI
   class BadBankSearchUseCase {
       fun searchBanks(query: String): List<BankListItem> {
           // Don't format data for specific UI components
           return banks.map { bank ->
               BankListItem(
                   title = bank.name,
                   subtitle = "${bank.location.distance}km away",
                   iconRes = R.drawable.ic_bank
               )
           }
       }
   }
   
   // ✅ GOOD: Use case returns domain models
   class GoodBankSearchUseCase {
       suspend fun searchBanks(query: String): Flow<Result<List<Bank>>> {
           // Return pure domain models
           return bankRepository.searchBanks(query)
       }
   }
   ```

2. **Framework Dependencies**
   ```kotlin
   // ❌ BAD: Android dependencies in use case
   class BadLocationUseCase(
       private val context: Context // ❌ Don't inject Android types
   ) {
       fun getCurrentLocation(): Location {
           val locationManager = context.getSystemService(LocationManager::class.java)
           // ... Android-specific code
       }
   }
   
   // ✅ GOOD: Abstract dependencies
   class GoodLocationUseCase @Inject constructor(
       private val locationRepository: LocationRepository // ✅ Use abstractions
   ) {
       suspend fun getCurrentLocation(): Result<Location> {
           return locationRepository.getCurrentLocation()
       }
   }
   ```

3. **Data Source Implementation Details**
   ```kotlin
   // ❌ BAD: Use case knows about database specifics
   class BadBankUseCase {
       fun getBanks(): Flow<List<Bank>> {
           return dao.getAllBanks()
               .map { entities -> entities.map { it.toDomain() } }
       }
   }
   
   // ✅ GOOD: Use case works with repository abstraction
   class GoodBankUseCase @Inject constructor(
       private val bankRepository: BankRepository
   ) {
       fun getBanks(): Flow<Result<List<Bank>>> {
           return bankRepository.getAllBanks()
       }
   }
   ```

## Use Case Patterns

### 1. Single Responsibility Pattern

Each use case should do one thing well:

```kotlin
// ✅ GOOD: Single responsibility
class GetBankDetailsUseCase @Inject constructor(
    private val bankRepository: BankRepository
) {
    suspend operator fun invoke(bankId: BankId): Flow<Result<Bank?>> {
        return bankRepository.getBankById(bankId)
    }
}

class GetBankReviewsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(bankId: BankId): Flow<Result<List<Review>>> {
        return reviewRepository.getReviewsForBank(bankId)
    }
}

// ❌ BAD: Multiple responsibilities
class GetBankWithReviewsUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    private val reviewRepository: ReviewRepository
) {
    // This could be split into separate use cases
}
```

### 2. Functional Style Pattern

For simple transformations, consider functional approach:

```kotlin
class CalculateDistanceUseCase @Inject constructor() {
    operator fun invoke(from: Location, to: Location): Double {
        return haversineDistance(from, to)
    }
    
    private fun haversineDistance(from: Location, to: Location): Double {
        // Pure function calculation
    }
}
```

### 3. Flow Transformation Pattern

For reactive data streams:

```kotlin
class FilterOpenBanksUseCase @Inject constructor() {
    operator fun invoke(banks: Flow<List<Bank>>): Flow<List<Bank>> {
        return banks.map { bankList ->
            val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
            bankList.filter { it.isOpen(currentTime) }
        }
    }
}
```

## Threading in Use Cases

### Core Threading Principles

Use cases should be **dispatcher-agnostic** and delegate threading decisions to the data layer. However, when use cases need to perform CPU-intensive operations or coordinate multiple data sources, proper threading becomes crucial.

### 1. Use Case Threading Patterns

#### Pattern 1: CPU-Intensive Operations

For use cases that perform heavy calculations:

```kotlin
class CalculateBankRouteOptimizationUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        startLocation: Location,
        bankIds: List<BankId>
    ): Result<OptimizedRoute> = withContext(defaultDispatcher) {
        try {
            // CPU-intensive route calculation
            val banks = bankIds.map { id ->
                async {
                    bankRepository.getBankById(id).first().getOrThrow()
                }
            }.awaitAll()
            
            // Complex algorithm running on Default dispatcher
            val optimizedRoute = calculateOptimalRoute(startLocation, banks)
            
            Result.success(optimizedRoute)
        } catch (e: Exception) {
            Result.failure(RouteCalculationException("Failed to calculate route", e))
        }
    }
    
    private fun calculateOptimalRoute(
        startLocation: Location, 
        banks: List<Bank>
    ): OptimizedRoute {
        // CPU-intensive algorithm
        // This runs on Default dispatcher due to withContext above
        return TravelingSalesmanSolver.solve(startLocation, banks)
    }
}
```

#### Pattern 2: Concurrent Data Fetching

For use cases that need to fetch data from multiple sources concurrently:

```kotlin
class GetBankDetailsSummaryUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    private val reviewRepository: ReviewRepository,
    private val queueRepository: QueueRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(bankId: BankId): Result<BankDetailsSummary> {
        return try {
            // Run concurrent operations on IO dispatcher
            withContext(ioDispatcher) {
                val bankDeferred = async { 
                    bankRepository.getBankById(bankId).first() 
                }
                val reviewsDeferred = async { 
                    reviewRepository.getReviewsForBank(bankId).first() 
                }
                val queueDeferred = async { 
                    queueRepository.getCurrentQueueInfo(bankId).first() 
                }
                
                // Await all results
                val bank = bankDeferred.await().getOrThrow()
                val reviews = reviewsDeferred.await().getOrElse { emptyList() }
                val queueInfo = queueDeferred.await().getOrNull()
                
                Result.success(
                    BankDetailsSummary(
                        bank = bank,
                        averageRating = reviews.map { it.rating }.average(),
                        reviewCount = reviews.size,
                        estimatedWaitTime = queueInfo?.estimatedWaitMinutes
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(BankDetailsException("Failed to load bank details", e))
        }
    }
}
```

#### Pattern 3: Flow Composition with Threading

For reactive use cases that combine multiple Flow sources:

```kotlin
class GetNearbyBanksWithLiveUpdatesUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    private val locationRepository: LocationRepository,
    private val queueRepository: QueueRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    operator fun invoke(radiusKm: Double): Flow<Result<List<BankWithQueueInfo>>> {
        return locationRepository.getCurrentLocationUpdates()
            .flatMapLatest { locationResult ->
                locationResult.fold(
                    onSuccess = { location ->
                        // Combine banks and queue info
                        combine(
                            bankRepository.getBanksNearLocation(location, radiusKm),
                            queueRepository.getAllQueueUpdates()
                        ) { banksResult, queueUpdates ->
                            banksResult.map { banks ->
                                banks.map { bank ->
                                    val queueInfo = queueUpdates.find { it.bankId == bank.id }
                                    BankWithQueueInfo(bank, queueInfo)
                                }
                            }
                        }
                    },
                    onFailure = { error ->
                        flowOf(Result.failure(error))
                    }
                )
            }
            .flowOn(defaultDispatcher) // Process combinations on Default dispatcher
    }
}
```

### 2. Flow Threading Best Practices

#### Repository Flows Should Handle Their Own Threading

```kotlin
// ✅ GOOD: Repository handles threading
class BankRepositoryImpl @Inject constructor(
    private val localDataSource: BankLocalDataSource,
    private val remoteDataSource: BankRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BankRepository {
    
    override fun getBanksNearLocation(
        location: Location,
        radiusKm: Double
    ): Flow<Result<List<Bank>>> {
        return localDataSource.getBanksNearLocation(location, radiusKm)
            .flowOn(ioDispatcher) // Repository handles threading
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}

// ✅ GOOD: Use case doesn't need to worry about threading
class FindNearbyBanksUseCase @Inject constructor(
    private val bankRepository: BankRepository
) {
    operator fun invoke(
        location: Location,
        radiusKm: Double
    ): Flow<Result<List<Bank>>> {
        // Repository already handles threading
        return bankRepository.getBanksNearLocation(location, radiusKm)
    }
}
```

#### Use flowOn() for Transformation Operations

```kotlin
class ProcessBankDataUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<Result<List<ProcessedBank>>> {
        return bankRepository.getAllBanks()
            .map { result ->
                result.map { banks ->
                    // CPU-intensive processing
                    banks.map { bank -> 
                        ProcessedBank(
                            bank = bank,
                            popularityScore = calculatePopularityScore(bank),
                            recommendationRank = calculateRecommendationRank(bank)
                        )
                    }
                }
            }
            .flowOn(defaultDispatcher) // Move heavy processing to Default dispatcher
    }
    
    private fun calculatePopularityScore(bank: Bank): Double {
        // CPU-intensive calculation
        return /* complex algorithm */ 0.0
    }
}
```

### 3. Error Handling Across Dispatchers

```kotlin
class ComplexBankAnalysisUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    private val analyticsRepository: AnalyticsRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(bankIds: List<BankId>): Result<AnalysisReport> {
        return try {
            supervisorScope {
                // Fetch data concurrently on IO dispatcher
                val banksDeferred = bankIds.map { bankId ->
                    async(ioDispatcher) {
                        bankRepository.getBankById(bankId).first().getOrThrow()
                    }
                }
                
                val banks = banksDeferred.awaitAll()
                
                // Process data on Default dispatcher
                val analysisResult = withContext(defaultDispatcher) {
                    performComplexAnalysis(banks)
                }
                
                // Save results on IO dispatcher
                withContext(ioDispatcher) {
                    analyticsRepository.saveAnalysisReport(analysisResult)
                }
                
                Result.success(analysisResult)
            }
        } catch (e: CancellationException) {
            throw e // Don't catch cancellation
        } catch (e: Exception) {
            Result.failure(AnalysisException("Analysis failed", e))
        }
    }
    
    private fun performComplexAnalysis(banks: List<Bank>): AnalysisReport {
        // CPU-intensive work that should run on Default dispatcher
        return AnalysisReport(/* ... */)
    }
}
```

### 4. Testing with Dispatchers

#### Using TestDispatchers

```kotlin
@ExtendWith(MockitoExtension::class)
class CalculateBankRouteOptimizationUseCaseTest {

    @Mock
    private lateinit var bankRepository: BankRepository
    
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var useCase: CalculateBankRouteOptimizationUseCase
    
    @BeforeEach
    fun setup() {
        useCase = CalculateBankRouteOptimizationUseCase(
            bankRepository = bankRepository,
            defaultDispatcher = testDispatcher // Inject test dispatcher
        )
    }
    
    @Test
    fun `should calculate optimal route successfully`() = runTest(testDispatcher) {
        // Given
        val startLocation = TestData.sampleLocation
        val bankIds = TestData.sampleBankIds
        val banks = TestData.sampleBanks
        
        bankIds.forEach { bankId ->
            given(bankRepository.getBankById(bankId))
                .willReturn(flowOf(Result.success(banks.find { it.id == bankId }!!)))
        }
        
        // When
        val result = useCase(startLocation, bankIds)
        
        // Then
        result.shouldBeSuccess()
        val route = result.getOrNull()!!
        route.stops shouldHaveSize bankIds.size
    }
}
```

#### Testing Flow Threading

```kotlin
@Test
fun `should handle concurrent flow operations correctly`() = runTest {
    // Given
    val testScheduler = testScheduler
    val bankFlow = flow {
        delay(100)
        emit(Result.success(TestData.sampleBanks))
    }
    val queueFlow = flow {
        delay(50)
        emit(TestData.sampleQueueUpdates)
    }
    
    given(bankRepository.getBanksNearLocation(any(), any()))
        .willReturn(bankFlow)
    given(queueRepository.getAllQueueUpdates())
        .willReturn(queueFlow)
    
    // When
    val results = useCase(10.0).toList()
    
    // Then - verify timing and results
    results shouldHaveSize 1
    testScheduler.advanceTimeBy(100)
    // Additional assertions...
}
```

### 5. Performance Considerations

1. **Prefer Flow.flowOn() over withContext()** for stream processing
2. **Use supervisorScope** when you want independent failure handling
3. **Limit concurrency** for resource-intensive operations:

```kotlin
class BatchProcessingUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    suspend fun processBankData(banks: List<Bank>): Result<List<ProcessedBank>> {
        return withContext(defaultDispatcher) {
            banks.chunked(BATCH_SIZE) // Process in batches
                .map { batch ->
                    batch.map { bank ->
                        async { processBank(bank) }
                    }.awaitAll()
                }
                .flatten()
                .let { Result.success(it) }
        }
    }
    
    companion object {
        private const val BATCH_SIZE = 10
    }
}
```

## Testing Use Cases

### Unit Testing Example

```kotlin
@ExtendWith(MockitoExtension::class)
class FindNearbyBanksUseCaseTest {

    @Mock
    private lateinit var bankRepository: BankRepository
    
    @Mock
    private lateinit var locationRepository: LocationRepository
    
    @Mock
    private lateinit var userPreferencesRepository: UserPreferencesRepository
    
    private lateinit var useCase: FindNearbyBanksUseCase
    
    @BeforeEach
    fun setup() {
        useCase = FindNearbyBanksUseCase(
            bankRepository,
            locationRepository,
            userPreferencesRepository
        )
    }
    
    @Test
    fun `invoke should return banks when location is available`() = runTest {
        // Given
        val location = TestData.sampleLocation
        val banks = TestData.sampleBanks
        val radius = 10.0
        
        given(locationRepository.getCurrentLocation())
            .willReturn(Result.success(location))
        given(userPreferencesRepository.getPreferredSearchRadius())
            .willReturn(flowOf(radius))
        given(bankRepository.getBanksNearLocation(location, radius))
            .willReturn(flowOf(Result.success(banks)))
        
        // When
        val result = useCase().first()
        
        // Then
        result.shouldBeSuccess()
        result.getOrNull() shouldBe banks
    }
    
    @Test
    fun `invoke should return failure when location is unavailable`() = runTest {
        // Given
        given(locationRepository.getCurrentLocation())
            .willReturn(Result.failure(LocationUnavailableException()))
        
        // When
        val result = useCase().first()
        
        // Then
        result.shouldBeFailure()
        result.exceptionOrNull() shouldBe instanceOf<LocationUnavailableException>()
    }
}
```

## Best Practices

### 1. Naming Conventions

```kotlin
// ✅ GOOD: Descriptive, action-oriented names
class GetUserProfileUseCase
class UpdateUserPreferencesUseCase
class ValidateEmailAddressUseCase
class CalculateMonthlyInterestUseCase

// ❌ BAD: Vague or noun-based names
class UserUseCase
class DataUseCase
class BankStuff
```

### 2. Error Handling

```kotlin
class GetBankDetailsUseCase @Inject constructor(
    private val bankRepository: BankRepository
) {
    suspend operator fun invoke(bankId: BankId): Result<Bank> {
        return try {
            bankRepository.getBankById(bankId)
                .map { bank ->
                    bank ?: throw BankNotFoundException("Bank with ID ${bankId.value} not found")
                }
                .first()
        } catch (e: Exception) {
            Result.failure(
                when (e) {
                    is BankNotFoundException -> e
                    is NetworkException -> BankDataUnavailableException("Cannot fetch bank data", e)
                    else -> UnknownBankException("Unexpected error fetching bank", e)
                }
            )
        }
    }
}
```

### 3. Dependency Injection

```kotlin
// Use case modules
@Module
@InstallIn(ViewModelComponent::class)
abstract class BankUseCaseModule {
    
    @Binds
    abstract fun bindFindNearbyBanksUseCase(
        impl: FindNearbyBanksUseCase
    ): FindNearbyBanksUseCase
}
```

### 4. Documentation

```kotlin
/**
 * Calculates the optimal route between multiple bank locations.
 * 
 * This use case applies business rules for route optimization:
 * - Prioritizes banks with shorter wait times
 * - Considers bank operating hours
 * - Respects user's preferred maximum travel distance
 * 
 * @param startLocation The user's starting location
 * @param bankIds List of banks to visit (minimum 2, maximum 10)
 * @param optimizationCriteria How to optimize the route (time, distance, or wait)
 * 
 * @return Flow of route optimization result
 * @throws InvalidRouteException when bankIds list is invalid
 * @throws LocationUnavailableException when startLocation is invalid
 */
class OptimizeBankRouteUseCase @Inject constructor(
    private val bankRepository: BankRepository,
    private val routingRepository: RoutingRepository
) {
    // Implementation...
}
```

## Integration with Architecture

### ViewModel Usage

```kotlin
@HiltViewModel
class BankListViewModel @Inject constructor(
    private val findNearbyBanksUseCase: FindNearbyBanksUseCase,
    private val toggleBankFavoriteUseCase: ToggleBankFavoriteUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(BankListUiState.Loading)
    val uiState: StateFlow<BankListUiState> = _uiState.asStateFlow()
    
    fun loadNearbyBanks(radiusKm: Double = 10.0) {
        viewModelScope.launch {
            findNearbyBanksUseCase(radiusKm)
                .collect { result ->
                    _uiState.value = result.fold(
                        onSuccess = { banks -> BankListUiState.Success(banks) },
                        onFailure = { error -> BankListUiState.Error(error.message ?: "Unknown error") }
                    )
                }
        }
    }
    
    fun toggleFavorite(bankId: BankId) {
        viewModelScope.launch {
            toggleBankFavoriteUseCase(bankId)
                .onFailure { error ->
                    // Handle error (show snackbar, etc.)
                }
        }
    }
}
```

## Anti-Patterns to Avoid

1. **God Use Cases**: Use cases that do too many things
2. **Anemic Use Cases**: Use cases that just delegate to repository without adding value
3. **Chatty Use Cases**: Making multiple repository calls when one would suffice
4. **Leaky Abstractions**: Exposing data source implementation details
5. **UI Coupling**: Returning UI-specific data structures

## Summary

The domain layer is the heart of your application's business logic. Use cases serve as the entry points that orchestrate domain operations while remaining independent of external concerns. They should be:

- **Pure**: No side effects except through injected dependencies
- **Testable**: Easy to unit test with mocked dependencies  
- **Single-purpose**: Each use case has one clear responsibility
- **Framework-agnostic**: No Android or external framework dependencies
- **Business-focused**: Implement business rules and policies

By following these patterns and principles, you'll create a maintainable and testable domain layer that forms the solid foundation of your clean architecture. 