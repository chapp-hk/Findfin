# Module: `:core:location:ui-foundation`

The `:core:location:ui-foundation` module provides foundational UI components and utilities for managing location settings in a Compose-based application. It includes abstractions and implementations to handle location settings state and interactions with the user.

## Features

- **Location Settings State Management**: Provides interfaces and implementations to manage the state of location settings.
- **Composable Utilities**: Includes composable functions to remember and interact with location settings.
- **Helper Abstractions**: Defines helpers for interacting with location settings and prompting the user to enable them.

## Components

### 1. `LocationSettingState`
An interface representing the state of location settings, including methods to check the current status and prompt the user to enable location settings.

#### Key Features:
- Tracks the current status of location settings (`Enabled`, `Disabled`, `NoSensor`).
- Provides a method to launch an intent for enabling location settings.

### 2. `MutableLocationSettingState`
An implementation of `LocationSettingState` that manages the mutable state of location settings.

#### Key Features:
- Uses `SettingHelper` to interact with location settings.
- Provides a coroutine-based mechanism to refresh the location settings state.

### 3. `SettingHelper`
An interface defining methods to interact with location settings, such as retrieving the current status and creating an intent to enable location settings.

#### Key Features:
- Abstracts platform-specific logic for location settings.
- Provides a static composition local (`LocalSettingHelper`) for dependency injection in Compose.

### 4. Composable Utilities
- `rememberLocationSettingState`: A composable function to remember and provide a `LocationSettingState` instance.
- `rememberMutableLocationSettingState`: A composable function to remember and provide a `MutableLocationSettingState` instance.

## Usage

### Example: Managing Location Settings State

```kotlin
@Composable
fun LocationSettingsScreen(settingHelper: SettingHelper) {
    val locationSettingState = rememberLocationSettingState(settingHelper) { state ->
        // Handle state updates
        println("Location setting status: ${state.status}")
    }

    when (locationSettingState.status) {
        is LocationSettingStatus.NoSensor -> {
            Text("No GPS sensor available")
        }
        is LocationSettingStatus.Enabled -> {
            Text("Location is enabled")
        }
        is LocationSettingStatus.Disabled -> {
            Button(onClick = { locationSettingState.launchEnableLocation() }) {
                Text("Enable Location")
            }
        }
    }
}
```

### Integration

1. Add the dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation(project(":core:location:ui-foundation"))
}
```

2. Provide a `SettingHelper` implementation using `LocalSettingHelper` in your composable hierarchy.

## Dependencies

This module depends on:
- Jetpack Compose for UI components.
- Coroutine support for asynchronous operations.

## License

This module is part of the application and follows the licensing terms of the overall project.
