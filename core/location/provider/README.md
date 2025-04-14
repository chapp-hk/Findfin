# Module: `:core:location:provider`

The `:core:location:provider` module serves as the foundation for location-related functionality in the application. It is structured into two submodules: `:api` and `:impl`, ensuring a clean separation of concerns between abstractions and platform-specific implementations.

## Submodules

### 1. `:core:location:provider:api`
This submodule defines the abstractions and data models for location management. It provides a platform-independent interface for retrieving location data.

#### Key Features:
- **Abstractions**: Defines the `LocationProviderManager` interface for location retrieval.
- **Data Models**: Includes `Location` and `LocationResult` for representing location data and request outcomes.

#### Integration:
Add the following dependency to use the API module:

```kotlin
dependencies {
    implementation(project(":core:location:provider:api"))
}
```

For more details, refer to the [README](../api/README.md) of the `:core:location:provider:api` module.

---

### 2. `:core:location:provider:impl`
This submodule provides the platform-specific implementation of the abstractions defined in the `:api` module. It uses Google Play Services to fetch location data.

#### Key Features:
- Implements the `LocationProviderManager` interface.
- Handles location permissions and error scenarios.
- Uses `FusedLocationProviderClient` for location retrieval.

#### Integration:
Add the following dependency to use the implementation module:

```kotlin
dependencies {
    implementation(project(":core:location:provider:impl"))
    implementation("com.google.android.gms:play-services-location:<version>")
}
```

For more details, refer to the [README](../impl/README.md) of the `:core:location:provider:impl` module.

---

## Usage

To use the location provider functionality:
1. Add dependencies for both `:api` and `:impl` modules.
2. Inject the `LocationProviderManager` interface in your use case or application layer.
3. Handle the `LocationResult` to process location data.

### Example:

```kotlin
class ExampleUseCase @Inject constructor(
    private val locationProviderManager: LocationProviderManager,
) {
    suspend fun fetchLocation() {
        val result = locationProviderManager.getCurrentLocation()
        when (result) {
            LocationResult.LocationUnavailable -> {
                println("Location is unavailable.")
            }
            LocationResult.Error -> {
                println("Error retrieving location.")
            }
            is LocationResult.Success -> {
                println("Location: ${result.location.latitude}, ${result.location.longitude}")
            }
        }
    }
}
```
