# Module: `:core:locale:impl`

The `:core:locale:impl` module provides the implementation of the locale management functionality defined in the `:core:locale:api` module. It includes the concrete classes and logic required to manage application locales, such as setting the current locale, retrieving available languages, and handling locale-related operations.

## Key Features

- **Locale Management Implementation**: Implements the `LocaleProviderManager` interface to manage application locales.
- **Language Support**: Provides the logic to retrieve and handle supported languages.
- **Integration with System Locale**: Ensures seamless integration with the system's locale settings.

## Components

### 1. `LocaleProviderManagerImpl`
A concrete implementation of the `LocaleProviderManager` interface. It provides:
- Methods to set and retrieve the application's current locale.
- Logic to fetch the list of available languages.

### 2. Language Data Source
Handles the retrieval of supported languages, ensuring they are properly mapped and localized.

### 3. Locale Utilities
Includes utility classes or functions to assist with locale-related operations, such as parsing and formatting locale tags.

## Usage

### Dependency
To use this module, add the following dependency to your module's `build.gradle` file:
```gradle
implementation project(":core:locale:impl")
```

### Example
This module is typically used internally and is not directly accessed by other modules. Instead, it is bound to the `LocaleProviderManager` interface via dependency injection (e.g., using Dagger Hilt).

Here is an example of how it integrates with the `:core:locale:api` module:
```kotlin
@HiltModule
@Module(SingletonComponent::class)
abstract class LocaleModule {
    @Binds
    abstract fun bindLocaleProviderManager(
        impl: LocaleProviderManagerImpl
    ): LocaleProviderManager
}
```

## Integration
This module is designed to work with the `:core:locale:api` module and is consumed by other modules, to provide locale management functionality. It ensures a clean separation of concerns by encapsulating the implementation details of locale management.
