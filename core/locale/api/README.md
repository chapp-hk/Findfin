# Module: `:core:locale:api`

The `:core:locale:api` module provides the core API for managing application locales. It defines interfaces, data models, and result types that allow other modules to interact with and manage the application's locale settings in a consistent and centralized manner.

## Key Features

- **Locale Management**: Provides functionality to set and retrieve the application's current locale.
- **Available Languages**: Exposes a list of supported languages for the application.
- **Result Handling**: Uses a sealed class to represent the result of locale-related operations, ensuring robust error handling.

## Components

### 1. `LocaleProviderManager`
An interface for managing application locales. It provides methods to:
- Set the application locale.
- Retrieve the current locale and its language tag.
- Fetch a list of available languages.

### 2. `Language`
A data model representing a language option available in the application. It includes:
- `isDefault`: Indicates if the language is the default.
- `localeTag`: The language tag (e.g., "en", "zh").
- `displayName`: The localized display name of the language.

### 3. `LocaleResult`
A sealed class representing the result of locale-related operations. It includes:
- `Custom`: Represents a custom locale with a specific tag and display name.
- `Default`: Represents the system's default locale.
- `Error`: Represents an error state when the locale cannot be determined.

## Usage

### Dependency
To use this module, add the following dependency to your module's `build.gradle` file:
```gradle
implementation project(":core:locale:api")
```

### Example
Here is an example of how to use the `LocaleProviderManager`:
```kotlin
val localeProviderManager: LocaleProviderManager = // Obtain instance via DI

// Set a new locale
localeProviderManager.setLocale("en")

// Get the current locale
val currentLocale = localeProviderManager.getCurrentLocale()

// Fetch available languages
val availableLanguages = localeProviderManager.getAvailableLanguages()
```

## Integration
This module is designed to be consumed by other modules, to provide locale management functionality. It ensures a clean separation of concerns and promotes reusability across the application.
