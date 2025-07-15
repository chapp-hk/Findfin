# Preferences Provider API

The Preferences Provider API module defines the contract for managing application preferences in a reactive way using Kotlin coroutines and Flow. This module provides the `AppPreferencesManager` interface that abstracts preferences storage implementation details.

## Overview

This module is part of the core preferences system and serves as the API layer that:
- Defines the contract for preferences management
- Provides type-safe methods for boolean and string preferences
- Uses reactive programming patterns with Flow
- Maintains clean separation between API and implementation

## Features

- **Type-safe preferences**: Separate methods for boolean and string values
- **Reactive programming**: Uses Kotlin Flow for observing preference changes
- **Asynchronous operations**: Coroutine-based API for non-blocking preference updates
- **Clean architecture**: Interface-based design for easy testing and dependency injection

## API Reference

### AppPreferencesManager Interface

The main interface provides four essential methods:

```kotlin
interface AppPreferencesManager {
    suspend fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Flow<Boolean>
    
    suspend fun setString(key: String, value: String)
    fun getString(key: String): Flow<String>
}
```

#### Methods

- **`setBoolean(key: String, value: Boolean)`**: Sets a boolean preference value
- **`getBoolean(key: String): Flow<Boolean>`**: Observes a boolean preference value
- **`setString(key: String, value: String)`**: Sets a string preference value  
- **`getString(key: String): Flow<String>`**: Observes a string preference value

## Usage Examples

### Basic Usage

```kotlin
class MyRepository @Inject constructor(
    private val appPreferencesManager: AppPreferencesManager
) {
    suspend fun saveUserSetting(enabled: Boolean) {
        appPreferencesManager.setBoolean("user_setting_enabled", enabled)
    }
    
    fun observeUserSetting(): Flow<Boolean> {
        return appPreferencesManager.getBoolean("user_setting_enabled")
    }
}
```

### In ViewModels

```kotlin
class SettingsViewModel @Inject constructor(
    private val appPreferencesManager: AppPreferencesManager
) : ViewModel() {
    
    fun updateTheme(theme: String) {
        viewModelScope.launch {
            appPreferencesManager.setString("theme_preference", theme)
        }
    }
    
    val currentTheme: Flow<String> = appPreferencesManager.getString("theme_preference")
}
```

## Dependencies

This module has minimal dependencies:

```kotlin
dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
```

## Implementation

The actual implementation is provided by the `:core:preferences:provider:impl` module, which uses Android's DataStore internally. This separation allows for:

- Easy testing with mock implementations
- Clean dependency injection setup
- Potential future implementation changes without API changes

## Testing

When testing components that use `AppPreferencesManager`, use mocking:

```kotlin
private val appPreferencesManager = mockk<AppPreferencesManager>(relaxed = true)

@Test
fun `test preference usage`() {
    every { appPreferencesManager.getBoolean(any()) } returns flowOf(true)
    
    // Your test logic here
    
    coVerify { appPreferencesManager.setBoolean("key", true) }
}
```

## Module Structure

```
core/preferences/provider/api/
├── build.gradle.kts
├── src/main/kotlin/org/chapp/findfin/core/preferences/provider/api/
│   └── AppPreferencesManager.kt
└── README.md
```

## Architecture

This module follows the clean architecture pattern:
- **Pure Kotlin**: No Android dependencies in the API layer
- **Interface segregation**: Single responsibility for preferences management
- **Dependency inversion**: Higher-level modules depend on this abstraction 