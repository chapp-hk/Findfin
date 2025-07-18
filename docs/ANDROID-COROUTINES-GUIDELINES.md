# Android Coroutines Coding Guidelines

## Table of Contents
- [Overview](#overview)
- [Core Principles](#core-principles)
- [Coroutine Builders](#coroutine-builders)
- [Structured Concurrency](#structured-concurrency)
- [Flow Patterns](#flow-patterns)
- [Error Handling](#error-handling)
- [Threading and Dispatchers](#threading-and-dispatchers)
- [Android Integration](#android-integration)
- [Testing Coroutines](#testing-coroutines)
- [Performance Guidelines](#performance-guidelines)
- [Common Patterns](#common-patterns)
- [Anti-Patterns to Avoid](#anti-patterns-to-avoid)

## Overview

This guide establishes best practices for using Kotlin coroutines in our Android application. Coroutines provide a clean, efficient way to handle asynchronous operations while maintaining readability and performance.

### Key Benefits
- **Lightweight**: Coroutines are more efficient than threads
- **Structured Concurrency**: Automatic cancellation and exception handling
- **Flow Integration**: Reactive programming with lifecycle awareness
- **Testability**: Easy to test with TestDispatchers

## Core Principles

### 1. Structured Concurrency
Always use structured concurrency to ensure proper cancellation and exception handling:

```kotlin
// ✅ Good - Using structured concurrency
class MyRepository {
    suspend fun fetchData(): Result<Data> = withContext(Dispatchers.IO) {
        try {
            val data = apiService.getData()
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 2. Prefer suspend functions over callbacks
```kotlin
// ✅ Good - Suspend function
suspend fun loadUser(id: String): User = withContext(Dispatchers.IO) {
    userApi.getUser(id)
}

// ❌ Avoid - Callback-based approach
fun loadUser(id: String, callback: (User?) -> Unit) {
    // Callback hell
}
```

### 3. Use appropriate coroutine scopes
```kotlin
// ✅ Good - ViewModel scope
class MyViewModel : ViewModel() {
    fun loadData() {
        viewModelScope.launch {
            // Automatically cancelled when ViewModel is cleared
        }
    }
}
```

## Coroutine Builders

### launch
Use for fire-and-forget operations:
```kotlin
// ✅ Good - Fire and forget
viewModelScope.launch {
    analyticsService.trackEvent("user_action")
}
```

### async
Use when you need a result from concurrent operations:
```kotlin
// ✅ Good - Concurrent operations
suspend fun loadUserProfile(userId: String): UserProfile = coroutineScope {
    val userDeferred = async { userService.getUser(userId) }
    val preferencesDeferred = async { preferencesService.getPreferences(userId) }
    
    UserProfile(
        user = userDeferred.await(),
        preferences = preferencesDeferred.await()
    )
}
```

### runBlocking
⚠️ **Use sparingly** - Only in tests or main functions:
```kotlin
// ✅ Acceptable - Test usage
@Test
fun testSuspendFunction() = runBlocking {
    val result = repository.getData()
    assertEquals(expected, result)
}

// ❌ Avoid - In production code
fun badExample() {
    runBlocking { // Blocks the calling thread
        repository.getData()
    }
}
```

## Structured Concurrency

### Use coroutineScope for parallel operations
```kotlin
// ✅ Good - Structured concurrency
suspend fun fetchUserData(userId: String): UserData = coroutineScope {
    val profile = async { profileService.getProfile(userId) }
    val settings = async { settingsService.getSettings(userId) }
    val friends = async { friendsService.getFriends(userId) }
    
    UserData(
        profile = profile.await(),
        settings = settings.await(),
        friends = friends.await()
    )
}
```

### Proper exception handling
```kotlin
// ✅ Good - Exception handling with supervisorScope
suspend fun processMultipleItems(items: List<Item>): List<ProcessResult> {
    return supervisorScope {
        items.map { item ->
            async {
                try {
                    ProcessResult.Success(processItem(item))
                } catch (e: Exception) {
                    ProcessResult.Error(item.id, e.message)
                }
            }
        }.awaitAll()
    }
}
```

## Flow Patterns

### Repository Pattern with Flow
```kotlin
interface UserRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun refreshUsers(): Result<Unit>
}

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userApi: UserApi
) : UserRepository {
    
    override fun getUsers(): Flow<List<User>> = userDao.getAllUsers()
        .map { entities -> entities.map { it.toDomain() } }
    
    override suspend fun refreshUsers(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val users = userApi.getUsers()
            userDao.insertAll(users.map { it.toEntity() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### StateFlow for UI State
```kotlin
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    
    init {
        loadUsers()
    }
    
    private fun loadUsers() {
        viewModelScope.launch {
            userRepository.getUsers()
                .catch { error ->
                    _uiState.update { it.copy(error = error.message) }
                }
                .collect { users ->
                    _uiState.update { it.copy(users = users, isLoading = false) }
                }
        }
    }
}
```

### Flow Transformation
```kotlin
// ✅ Good - Flow transformations
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    
    private val searchQuery = MutableStateFlow("")
    
    val searchResults: StateFlow<List<SearchResult>> = searchQuery
        .debounce(300) // Wait for user to stop typing
        .filter { it.length >= 2 } // Minimum query length
        .distinctUntilChanged() // Avoid duplicate searches
        .flatMapLatest { query ->
            searchRepository.search(query)
                .catch { emit(emptyList()) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
```

## Error Handling

### Use Result wrapper for operations that can fail
```kotlin
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Result<T> = withContext(dispatcher) {
    try {
        Result.Success(apiCall())
    } catch (throwable: Throwable) {
        Result.Error(throwable)
    }
}
```

### Flow error handling
```kotlin
// ✅ Good - Proper flow error handling
fun getUsersFlow(): Flow<List<User>> = flow {
    emit(userDao.getAllUsers())
}.catch { exception ->
    // Log error
    logger.e("Failed to get users", exception)
    // Emit fallback or rethrow
    emit(emptyList())
}
```

### CoroutineExceptionHandler
```kotlin
// ✅ Good - Global exception handler
class MyApplication : Application() {
    private val globalExceptionHandler = CoroutineExceptionHandler { _, exception ->
        // Log uncaught exceptions
        crashlytics.recordException(exception)
    }
    
    // Use in appropriate scopes
}
```

## Threading and Dispatchers

### Choose the right dispatcher
```kotlin
// ✅ Good - Appropriate dispatcher usage
class DataRepository {
    // I/O operations
    suspend fun fetchFromNetwork(): Data = withContext(Dispatchers.IO) {
        apiService.getData()
    }
    
    // CPU-intensive work
    suspend fun processData(data: Data): ProcessedData = withContext(Dispatchers.Default) {
        heavyProcessing(data)
    }
    
    // UI updates
    suspend fun updateUI(data: Data) = withContext(Dispatchers.Main) {
        updateViews(data)
    }
}
```

### Dispatcher Guidelines
- **Dispatchers.Main**: UI updates, light work
- **Dispatchers.IO**: Network, file I/O, database operations
- **Dispatchers.Default**: CPU-intensive work, parsing, sorting
- **Dispatchers.Unconfined**: Advanced use cases only

### Don't switch dispatchers unnecessarily
```kotlin
// ❌ Avoid - Unnecessary context switching
suspend fun badExample() = withContext(Dispatchers.IO) {
    withContext(Dispatchers.Main) {
        withContext(Dispatchers.IO) {
            // Too many context switches
        }
    }
}

// ✅ Good - Minimal context switching
suspend fun goodExample() = withContext(Dispatchers.IO) {
    val data = fetchData()
    processData(data)
}
```

## Android Integration

### ViewModel Integration
```kotlin
class MyViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()
    
    fun loadData() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val data = repository.getData()
                _state.value = UiState.Success(data)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
```

### Lifecycle-aware Flow collection
```kotlin
// ✅ Good - Lifecycle-aware collection
@Composable
fun MyScreen(viewModel: MyViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    when (state) {
        UiState.Loading -> LoadingIndicator()
        is UiState.Success -> SuccessContent(state.data)
        is UiState.Error -> ErrorMessage(state.message)
    }
}
```

### Repository Pattern
```kotlin
@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val userApi: UserApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    
    fun getUsers(): Flow<List<User>> = userDao.getAllUsers()
        .flowOn(dispatcher)
    
    suspend fun refreshUsers(): Result<Unit> = withContext(dispatcher) {
        safeApiCall { userApi.getUsers() }
            .onSuccess { users ->
                userDao.insertAll(users)
            }
    }
}
```

## Testing Coroutines

### Use TestDispatchers
```kotlin
@Test
fun testCoroutine() = runTest {
    // Test dispatcher automatically advances time
    val repository = MyRepository()
    val result = repository.getData()
    assertEquals(expected, result)
}
```

### Testing Flow
```kotlin
@Test
fun testFlow() = runTest {
    val flow = repository.getDataFlow()
    
    flow.test {
        assertEquals(expected1, awaitItem())
        assertEquals(expected2, awaitItem())
        awaitComplete()
    }
}
```

### Inject TestDispatcher for unit tests
```kotlin
class MyRepositoryTest {
    private val testDispatcher = StandardTestDispatcher()
    private val repository = MyRepository(testDispatcher)
    
    @Test
    fun testAsyncOperation() = runTest(testDispatcher) {
        // Test code
    }
}
```

## Performance Guidelines

### Use StateFlow.stateIn for expensive flows
```kotlin
// ✅ Good - Cache expensive operations
val expensiveData: StateFlow<Data> = repository.getExpensiveData()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Data.Empty
    )
```

### Limit concurrent operations
```kotlin
// ✅ Good - Limit concurrency
class MyRepository {
    private val semaphore = Semaphore(3) // Max 3 concurrent operations
    
    suspend fun processItem(item: Item): Result<ProcessedItem> {
        semaphore.withPermit {
            return processItemInternal(item)
        }
    }
}
```

### Use Flow operators efficiently
```kotlin
// ✅ Good - Efficient flow operations
val searchResults = searchQuery
    .debounce(300)
    .distinctUntilChanged()
    .filter { it.isNotBlank() }
    .flatMapLatest { query ->
        repository.search(query)
    }
```

## Common Patterns

### Repository with caching
```kotlin
@Singleton
class CachedRepository @Inject constructor(
    private val api: ApiService,
    private val cache: Cache
) {
    suspend fun getData(forceRefresh: Boolean = false): Data {
        if (!forceRefresh && cache.isValid()) {
            return cache.getData()
        }
        
        return withContext(Dispatchers.IO) {
            val data = api.getData()
            cache.saveData(data)
            data
        }
    }
}
```

### Retry pattern
```kotlin
suspend fun <T> retryOperation(
    times: Int = 3,
    delay: Long = 1000,
    operation: suspend () -> T
): T {
    repeat(times - 1) {
        try {
            return operation()
        } catch (e: Exception) {
            delay(delay)
        }
    }
    return operation() // Last attempt without catch
}
```

### Timeout pattern
```kotlin
suspend fun <T> withTimeout(
    timeoutMs: Long,
    operation: suspend () -> T
): Result<T> = try {
    Result.success(withTimeout(timeoutMs) { operation() })
} catch (e: TimeoutCancellationException) {
    Result.failure(e)
}
```

## Anti-Patterns to Avoid

### ❌ Don't use GlobalScope
```kotlin
// ❌ Bad - GlobalScope lives for app lifetime
GlobalScope.launch {
    // This won't be cancelled automatically
}

// ✅ Good - Use appropriate scope
viewModelScope.launch {
    // Cancelled when ViewModel is cleared
}
```

### ❌ Don't block in suspend functions
```kotlin
// ❌ Bad - Blocking in suspend function
suspend fun badFunction(): String {
    return runBlocking {
        someOperation()
    }
}

// ✅ Good - Properly suspend
suspend fun goodFunction(): String {
    return withContext(Dispatchers.IO) {
        someOperation()
    }
}
```

### ❌ Don't ignore cancellation
```kotlin
// ❌ Bad - Ignoring cancellation
suspend fun badLoop() {
    while (true) {
        try {
            doWork()
        } catch (e: CancellationException) {
            // Don't catch and ignore cancellation
        }
    }
}

// ✅ Good - Respect cancellation
suspend fun goodLoop() {
    while (isActive) {
        doWork()
        yield() // Cooperative cancellation
    }
}
```

### ❌ Don't create unnecessary coroutines
```kotlin
// ❌ Bad - Unnecessary coroutine
suspend fun badExample(): String = coroutineScope {
    return@coroutineScope "Hello"
}

// ✅ Good - Direct return
suspend fun goodExample(): String = "Hello"
```

### ❌ Don't use Dispatchers.Main for heavy work
```kotlin
// ❌ Bad - Heavy work on main thread
viewModelScope.launch(Dispatchers.Main) {
    heavyComputation() // Will block UI
}

// ✅ Good - Use appropriate dispatcher
viewModelScope.launch(Dispatchers.Default) {
    val result = heavyComputation()
    withContext(Dispatchers.Main) {
        updateUI(result)
    }
}
```

## Conclusion

Following these guidelines will help ensure:
- **Maintainable Code**: Clear, readable coroutine usage
- **Performance**: Efficient resource utilization
- **Reliability**: Proper error handling and cancellation
- **Testability**: Easy to test asynchronous code

Remember: Always prefer structured concurrency, use appropriate dispatchers, and handle errors gracefully. When in doubt, favor simplicity and readability over premature optimization.
