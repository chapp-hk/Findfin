# Core Map Module

The `:core:map` module is a core component of our application that provides map-related functionalities.

## Features

- Provides a reusable map view that can be used across different features of the application.
- Handles map-related operations such as displaying markers, handling user interactions, and managing map state.

## Usage

To use the `:core:map` module in your feature module, add the following line to your `build.gradle.kts` file:

```kotlin
implementation(projects.core.map)
```

Then, you can use the `AppMap` composable in your UI:

```kotlin
@Composable
fun YourScreen() {
    AppMap(
        // Pass in your parameters here
    )
}
```

## Dependencies

The `:core:map` module depends on the following libraries:

- Google Maps SDK for Android
- AndroidX Compose
