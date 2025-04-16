# Module: `:core:locale`

The `:core:locale` module provides the foundation for managing application locales. It is structured into two submodules: `:api` and `:impl`, ensuring a clean separation of concerns between abstractions and their implementations.

## Submodules

### 1. `:core:locale:api`
This submodule defines the abstractions and data models for locale management. It provides a platform-independent interface for handling application locales.

#### Key Features:
- **Abstractions**: Defines the `LocaleProviderManager` interface for managing locales.
- **Data Models**: Includes `Language` and `LocaleResult` for representing language options and locale-related outcomes.

#### Integration:
Add the following dependency to use the API module:

```kotlin
dependencies {
    implementation(project(":core:locale:api"))
}
```

For more details, refer to the [README](./api/README.md) of the `:core:locale:api` module.

---

### 2. `:core:locale:impl`
This submodule provides the implementation of the abstractions defined in the `:api` module. It includes the logic for setting, retrieving, and managing application locales.

#### Key Features:
- Implements the `LocaleProviderManager` interface.
- Provides logic for retrieving available languages and handling locale changes.
- Integrates with system locale settings.

#### Integration:
Add the following dependency to use the implementation module:

```kotlin
dependencies {
    implementation(project(":core:locale:impl"))
}
```

For more details, refer to the [README](./impl/README.md) of the `:core:locale:impl` module.

---

## Usage

To use the locale management functionality:
1. Add dependencies for both `:api` and `:impl` modules.
2. Inject the `LocaleProviderManager` interface in your use case or application layer.
3. Use the provided methods to manage application locales.

### Example:

```kotlin
class ExampleUseCase @Inject constructor(
    private val localeProviderManager: LocaleProviderManager,
) {
    fun setLocale(localeTag: String) {
        localeProviderManager.setLocale(localeTag)
    }

    fun getAvailableLanguages(): List<Language> {
        return localeProviderManager.getAvailableLanguages()
    }

    fun getCurrentLocale(): LocaleResult {
        return localeProviderManager.getCurrentLocale()
    }
}
```
