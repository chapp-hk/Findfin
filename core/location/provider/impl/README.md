# Core Location Module

This module is responsible for providing location-related functionalities in the application.

## Key Components

1. **LocationHelper**: This is an interface defined in the `LocationHelper.kt` file. It declares a method `getSingleCurrentLocation()` which returns a `LocationResult`.

2. **LocationHelperImpl**: This is the implementation of the `LocationHelper` interface. It is defined in the `LocationHelperImpl.kt` file. It uses various utilities and providers to get the current location.

3. **LocationResult**: This is a sealed interface defined in the `LocationResult.kt` file. It represents the result of a location request and can be one of the following:
    - `Location`: Represents a successful location result with latitude and longitude.
    - `PermissionNotGranted`: Represents a failure due to lack of location permission.
    - `GpsNotSupported`: Represents a failure due to the device not supporting GPS.
    - `GpsIsOff`: Represents a failure due to GPS being turned off.
    - `UnknownError`: Represents an unknown failure.

## Usage

To use this module, you need to inject an instance of `LocationHelper` and call the `getSingleCurrentLocation()` method. This method is a suspend function and should be called from a coroutine. It returns a `LocationResult` which can be used to handle the result of the location request.

## Testing

Unit tests for this module should be written using JUnit 5 and MockK. The tests should cover all possible `LocationResult` outcomes.

## Dependencies

This module depends on several utilities and providers for checking permissions, GPS availability, and getting the current location. These dependencies should be provided via dependency injection.

## Note

Please ensure that the necessary permissions for accessing location are declared in the application's manifest file and are granted by the user at runtime.
