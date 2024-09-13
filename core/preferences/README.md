# Preferences Module

The Preferences module provides a way to manage application preferences using Android's DataStore. It includes methods to set and get boolean and string preferences.

## Features

- Set and get boolean preferences.
- Set and get string preferences.
- Uses Kotlin coroutines and Flow for asynchronous operations.

## Getting Started

### Prerequisites

- Android Studio
- Kotlin
- Gradle

### Installation

1. Add the module to your project.
2. Include the necessary dependencies in your `build.gradle.kts` file:

```kotlin
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.app.android.common)
    alias(libs.plugins.app.hilt.android)
}

android {
    namespace = "ch.app.hk.bank.locator.core.preferences.impl"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.core.threading)
    implementation(libs.androidx.datastore.preferences)

    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotest.assertions.core)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.test.runner)
}
```

## Usage

### Setting Up

1. Create an instance of `AppPreferencesManagerImpl` by providing a `DataStore` instance:

```kotlin
val dataStore: DataStore<Preferences> = // initialize your DataStore
val appPreferences = AppPreferencesManagerImpl(dataStore)
```

### Setting and Getting Preferences

#### Set a Boolean Value

```kotlin
appPreferences.setBoolean("key", true)
```

#### Get a Boolean Value

```kotlin
appPreferences.getBoolean("key").collect { value ->
    // Use the boolean value
}
```

#### Set a String Value

```kotlin
appPreferences.setString("key", "value")
```

#### Get a String Value

```kotlin
appPreferences.getString("key").collect { value ->
    // Use the string value
}
```

## Testing

Unit tests for the `AppPreferencesManagerImpl` class are provided in the `AppPreferencesManagerImplTest` class. The tests use the `PreferenceDataStoreFactory` to create a test `DataStore` and verify the functionality of the `setBoolean`, `getBoolean`, `setString`, and `getString` methods.

### Running Tests

To run the tests, use the following command:

```sh
./gradlew :core:preferences:pixel8api34DebugAndroidTest
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
