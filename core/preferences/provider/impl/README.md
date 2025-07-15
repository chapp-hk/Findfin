# Preferences Provider Implementation

The Preferences Provider Implementation module provides a concrete implementation of the `AppPreferencesManager` interface using Android's DataStore. This module handles the actual persistence and retrieval of application preferences with reactive programming patterns.

## Overview

This module implements the preferences API using modern Android recommended practices:
- **DataStore**: Uses Android DataStore for type-safe, asynchronous preference storage
- **Dependency Injection**: Fully integrated with Hilt for clean dependency management
- **Reactive Programming**: Provides reactive streams via Kotlin Flow
- **Thread Safety**: All operations are thread-safe and optimized for performance

## Features

- **DataStore Integration**: Leverages Android DataStore for reliable preference storage
- **Automatic Binding**: Uses `Hilt` for automatic interface binding
- **Default Values**: Provides sensible defaults (false for booleans, empty string for strings)
- **Type Safety**: Uses DataStore's type-safe preference keys
- **Reactive Updates**: Automatically emits changes to preference observers

## Implementation Details

### Core Components

#### 1. AppPreferencesManagerImpl
The main implementation class that bridges the API contract with DataStore:

```kotlin
@HiltWrapBindModule
internal class AppPreferencesManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : AppPreferencesManager
```

**Key Features:**
- Uses `booleanPreferencesKey()` and `stringPreferencesKey()` for type safety
- Implements reactive getters that return Flow<T>
- Provides suspend functions for setting values
- Returns default values when preferences don't exist

#### 2. DataStore Configuration
Centralized DataStore setup with proper Hilt integration:

```kotlin
internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences_datastore")

@Module
@InstallIn(SingletonComponent::class)
internal class DataStoreHiltModule {
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore
}
```

**Configuration Details:**
- DataStore name: `"preferences_datastore"`
- Scoped as singleton across the application
- Uses application context for proper lifecycle management

### Default Values

The implementation provides sensible defaults:
- **Boolean preferences**: Default to `false`
- **String preferences**: Default to empty string `""`

```kotlin
// Boolean default handling
preferences[booleanPreferencesKey(key)] ?: false

// String default handling  
preferences[stringPreferencesKey(key)].orEmpty()
```

## Usage

### Dependency Injection Setup

The module automatically binds the implementation to the interface:

```kotlin
// In your component that needs preferences
class MyRepository @Inject constructor(
    private val appPreferencesManager: AppPreferencesManager // Automatically injected
) {
    // Use the interface as normal
}
```

### Running Tests

```bash
# Run all instrumentation tests
./gradlew :core:preferences:provider:impl:connectedAndroidTest

# Run specific test class
./gradlew :core:preferences:provider:impl:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=org.chapp.findfin.core.preferences.provider.impl.AppPreferencesManagerImplTest
```

## Performance Considerations

### DataStore Benefits
- **Asynchronous**: All operations are non-blocking
- **Type Safety**: Compile-time type checking for preference keys
- **Atomic Updates**: Guarantees consistency during concurrent access
- **Efficient**: Minimal disk I/O with intelligent caching

### Memory Management
- DataStore instances are cached and reused
- Flows are cold streams that activate only when collected
- Automatic cleanup when no active collectors exist

## Architecture Integration

### Clean Architecture Compliance 