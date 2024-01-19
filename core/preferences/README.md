# App preferences module
Module for app preferences\
Provided `AppPreferecesRepository` for data layer

in data or domain layer `gradle.kts`
```kotlin
implementation(project(mapOf("path" to ":core:preferences:api")))
```

in ui layer `gradle.kts`
```kotlin
implementation(project(mapOf("path" to ":core:preferences:impl")))
```
