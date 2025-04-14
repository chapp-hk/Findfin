# Module: `:core:location:provider:api`

The `:core:location:provider:api` module provides abstractions and data models for handling location-related functionality in the application. It defines the core interfaces and data structures required for location management, ensuring a clean separation of concerns and platform independence.

## Features

- **Location Abstraction**: Provides a platform-independent representation of geographical locations.
- **Location Result Handling**: Defines a sealed interface for handling location request results, including success, error, and unavailable states.

## Components

### 1. `Location`
Represents a geographical location with latitude and longitude coordinates.

```kotlin
data class Location(
    val latitude: Double,
    val longitude: Double,
)
```

### 2. `LocationResult`
A sealed interface that represents the result of a location request. It includes the following types:
- `LocationUnavailable`: Indicates that the location is unavailable.
- `Error`: Indicates that an error occurred while retrieving the location.
- `Success`: Indicates that the location was successfully retrieved, containing the `Location` data.

```kotlin
sealed interface LocationResult {
    data object LocationUnavailable : LocationResult
    data object Error : LocationResult
    data class Success(val location: Location) : LocationResult
}
```

## Usage

This module is designed to be used by other modules that require location-related functionality. It provides the necessary abstractions to decouple the location provider implementation from the rest of the application.

### Example

```kotlin
fun handleLocationResult(result: LocationResult) {
    when (result) {
        LocationResult.LocationUnavailable -> {
            println("Location is unavailable.")
        }
        LocationResult.Error -> {
            println("An error occurred while retrieving the location.")
        }
        is LocationResult.Success -> {
            println("Location retrieved: ${result.location.latitude}, ${result.location.longitude}")
        }
    }
}
```

## Dependencies

This module does not depend on any platform-specific libraries or frameworks, making it reusable across different environments.

## Integration

To use this module in your project, add the following dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation(project(":core:location:provider:api"))
}
```
