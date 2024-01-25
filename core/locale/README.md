# App locale module

`AppLocaleManager`
Kotlin object that can directly use in UI layer to manage locale related functions

Wrapper function to set app locale
```kotlin
AppLocaleManager.setLocale(localeString)
```

Wrapper function to get current app locale
```kotlin
AppLocaleManager.getCurrentLocale()
```

Get available locales in app
```kotlin
AppLocaleManager.availableLocales(context)
```
