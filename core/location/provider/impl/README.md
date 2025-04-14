# Module: `:core:location:provider:impl`

The `:core:location:provider:impl` module provides the implementation of the location provider functionality using platform-specific APIs. It works in conjunction with the `:core:location:provider:api` module to deliver location data to the application.

## Features

- **Location Retrieval**: Implements the `LocationProviderManager` interface to fetch the current location of the device.
- **Platform-Specific Implementation**: Uses the `FusedLocationProviderClient` from Google Play Services to access location data.
- **Error Handling**: Handles scenarios where location data is unavailable or an error occurs during retrieval.

## Components

### 1. `LocationProviderManagerImpl`
The implementation of the `LocationProviderManager` interface that retrieves the current location using the `FusedLocationProviderClient`.

#### Key Features:
- Requires location permissions (`ACCESS_FINE_LOCATION` or `ACCESS_COARSE_LOCATION`).
- Returns a `LocationResult` indicating success, error, or unavailability.

```kotlin
internal class LocationProviderManagerImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : LocationProviderManager {
    @RequiresPermission(
        anyOf = [
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ],
    )
    override suspend fun getCurrentLocation(): LocationResult {
        // Implementation details
    }
}
```

## Usage

This module is designed to be used in conjunction with the `:core:location:provider:api` module. The `LocationProviderManagerImpl` is provided via dependency injection.

### Example Integration

1. Add the dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation(project(":core:location:provider:impl"))
    implementation(project(":core:location:provider:api"))
    implementation("com.google.android.gms:play-services-location:<version>")
}
```

2. Inject the `LocationProviderManager` in your application or use case:

```kotlin
class ExampleUseCase @Inject constructor(
    private val locationProviderManager: LocationProviderManager,
) {
    suspend fun fetchLocation() {
        val result = locationProviderManager.getCurrentLocation()
        // Handle the LocationResult
    }
}
```

## Dependencies

- `:core:location:provider:api`: Provides the abstractions for location management.
- Google Play Services (`com.google.android.gms:play-services-location`): Used for accessing location data.

## Permissions

Ensure that the following permissions are declared in your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```
