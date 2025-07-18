# File Naming Definitions

## Overview

This document defines the purpose and usage guidelines for common file naming conventions used throughout the codebase. Consistent naming helps maintain code organization and makes the codebase more navigable for developers.

## File Type Definitions

### Util

**Purpose**: Utility classes contain **pure functions** and **stateless operations** that can be used across multiple parts of the application.

**Characteristics**:
- Contains static/top-level functions
- No state or dependencies
- Pure functions with predictable input/output
- Can be used anywhere in the codebase
- Generally focused on data transformation or computation

**When to Use**:
- String manipulation functions
- Date/time formatting
- Mathematical calculations
- Data validation
- Type conversions
- Extension functions

**Examples**:
```kotlin
// StringUtil.kt
object StringUtil {
    fun formatPhoneNumber(phone: String): String = // ...
    fun isValidEmail(email: String): Boolean = // ...
}

// DateUtil.kt
fun Long.toFormattedDate(): String = // ...
fun String.parseToDate(): Date? = // ...

// ValidationUtil.kt
object ValidationUtil {
    fun isValidCreditCard(number: String): Boolean = // ...
    fun sanitizeInput(input: String): String = // ...
}
```

**Naming Convention**:
- `[Purpose]Util.kt` (e.g., `StringUtil.kt`, `DateUtil.kt`)
- `[Domain]Utils.kt` (e.g., `NetworkUtils.kt`, `CryptoUtils.kt`)

---

### Helper

**Purpose**: Helper classes provide **contextual assistance** and **convenience methods** for specific features or components. They may have limited state and dependencies.

**Characteristics**:
- May have dependencies (injected or passed)
- Provides convenience methods for specific contexts
- Can have limited state for caching or configuration
- Usually supports a particular feature or component
- More specialized than utilities

**When to Use**:
- UI-related convenience methods
- Feature-specific assistance
- Complex setup or configuration
- Wrapper around external libraries
- Component-specific operations

**Examples**:
```kotlin
// NavigationHelper.kt
@Singleton
class NavigationHelper @Inject constructor(
    private val navController: NavController
) {
    fun navigateToProfile(userId: String) = // ...
    fun navigateBack() = // ...
}

// PermissionHelper.kt
class PermissionHelper(private val activity: Activity) {
    fun requestLocationPermission() = // ...
    fun hasLocationPermission(): Boolean = // ...
}

// DatabaseHelper.kt
class DatabaseHelper @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun clearAllTables() = // ...
    suspend fun exportData(): String = // ...
}
```

**Naming Convention**:
- `[Feature]Helper.kt` (e.g., `NavigationHelper.kt`, `PermissionHelper.kt`)
- `[Component]Helper.kt` (e.g., `DatabaseHelper.kt`, `CacheHelper.kt`)

---

### Manager

**Purpose**: Manager classes **coordinate and orchestrate** complex operations, often managing **lifecycle**, **state**, or **multiple dependencies**.

**Characteristics**:
- Manages lifecycle and state
- Coordinates multiple components or services
- Often singleton or scoped instances
- Handles complex business logic
- May manage resources or connections
- Typically has significant dependencies

**When to Use**:
- Managing application-wide state
- Coordinating multiple services
- Resource management (connections, caches)
- Complex business workflows
- Service orchestration
- Lifecycle management

**Examples**:
```kotlin
// LocationManager.kt
@Singleton
class LocationManager @Inject constructor(
    private val locationProvider: LocationProvider,
    private val permissionManager: PermissionManager,
    private val settingsManager: SettingsManager
) {
    private var isTracking = false
    
    suspend fun startLocationTracking() = // ...
    suspend fun stopLocationTracking() = // ...
    fun getCurrentLocation(): Flow<Location> = // ...
}

// NotificationManager.kt
@Singleton
class NotificationManager @Inject constructor(
    private val context: Context,
    private val userPreferences: UserPreferences
) {
    fun showNotification(notification: AppNotification) = // ...
    fun cancelNotification(id: String) = // ...
    fun createNotificationChannel() = // ...
}

// CacheManager.kt
@Singleton
class CacheManager @Inject constructor(
    private val memoryCache: MemoryCache,
    private val diskCache: DiskCache
) {
    suspend fun <T> get(key: String, type: Class<T>): T? = // ...
    suspend fun put(key: String, value: Any) = // ...
    suspend fun clear() = // ...
}
```

**Naming Convention**:
- `[Domain]Manager.kt` (e.g., `LocationManager.kt`, `CacheManager.kt`)
- `[Feature]Manager.kt` (e.g., `DownloadManager.kt`, `SyncManager.kt`)

## Comparison Matrix

| Aspect | Util | Helper | Manager |
|--------|------|--------|---------|
| **State** | Stateless | Limited state | Stateful |
| **Dependencies** | None/Minimal | Some | Many |
| **Scope** | Global | Feature-specific | System-wide |
| **Complexity** | Simple | Medium | Complex |
| **Lifecycle** | No lifecycle | Component lifecycle | Managed lifecycle |
| **Injection** | Usually not injected | May be injected | Typically injected |
| **Examples** | String formatting, validation | UI helpers, wrappers | Resource management, coordination |

## Decision Guidelines

### Choose **Util** when:
- ✅ The code contains pure functions
- ✅ No external dependencies are needed
- ✅ The functionality is universally applicable
- ✅ The operations are stateless and predictable

### Choose **Helper** when:
- ✅ You need convenience methods for a specific feature
- ✅ The code requires some dependencies or context
- ✅ You're wrapping complex operations for easier use
- ✅ The functionality is specific to a component or feature

### Choose **Manager** when:
- ✅ You need to coordinate multiple components
- ✅ State management is required
- ✅ Resource lifecycle needs to be managed
- ✅ Complex business logic orchestration is involved
- ✅ The class needs to be application-scoped

## Anti-Patterns to Avoid

### ❌ Don't mix responsibilities
```kotlin
// Bad - Manager doing util work
class UserManager {
    fun formatUserName(name: String): String = // Should be in UserUtil
    fun saveUser(user: User) = // Manager responsibility ✅
}
```

### ❌ Don't create unnecessary complexity
```kotlin
// Bad - Over-engineering a simple utility
@Singleton
class StringManager @Inject constructor(
    private val config: AppConfig
) {
    fun capitalize(text: String): String = text.capitalize() // Should be StringUtil
}
```

### ❌ Don't use generic names
```kotlin
// Bad - Too generic
class Helper { } // What does it help with?
class Util { }   // What utility does it provide?
class Manager { } // What does it manage?

// Good - Specific names
class NavigationHelper { }
class DateUtil { }
class CacheManager { }
```

## Best Practices

### 1. Keep Utils Simple
```kotlin
// ✅ Good - Simple, focused utility
object CurrencyUtil {
    fun formatCurrency(amount: Double, locale: Locale): String = 
        NumberFormat.getCurrencyInstance(locale).format(amount)
}
```

### 2. Make Helpers Contextual
```kotlin
// ✅ Good - Helper with context
class AnalyticsHelper @Inject constructor(
    private val analytics: Analytics
) {
    fun trackScreenView(screenName: String) {
        analytics.track("screen_view", mapOf("screen" to screenName))
    }
}
```

### 3. Design Managers for Coordination
```kotlin
// ✅ Good - Manager coordinating multiple services
@Singleton
class SyncManager @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val notificationService: NotificationService
) {
    suspend fun performSync() {
        // Coordinate multiple services
    }
}
```

### 4. Use Appropriate Scope
```kotlin
// ✅ Good - Appropriate scoping
object DateUtil { } // No injection needed

@ActivityScoped
class PermissionHelper // Activity-specific

@Singleton
class CacheManager // Application-wide
```

## Conclusion

Following these naming conventions and guidelines helps maintain:
- **Code Organization**: Clear separation of concerns
- **Developer Experience**: Predictable file structure
- **Maintainability**: Easier to locate and modify code
- **Team Collaboration**: Consistent patterns across the codebase

When in doubt, consider the **complexity**, **dependencies**, and **scope** of your code to choose the appropriate naming convention. 