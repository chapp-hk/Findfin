# Convention Plugins

Defines project-specific convention plugins, used to keep a single source of truth for common module configurations.

Plugin ID naming convention:
app.plugin.{function_name}

Current list of convention plugins:
- [app.plugin.ktlint](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/ktlint/KtlintPlugin.kt)
  - apply [ktlint gradle plugin](https://github.com/JLLeitschuh/ktlint-gradle) to root project and all subprojects
  - configure `ktlintFormat` pre-commit git hook

- [app.plugin.detekt](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/detekt/DetektPlugin.kt)
  - apply [detekt gradle plugin](https://github.com/detekt/detekt) to root project and all subprojects

- [app.plugin.jacoco](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/jacoco/JacocoRootCoveragePlugin.kt)
  - configure jacoco coverage for root project

- [app.plugin.jvm](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/jvm/JvmPlugin.kt)
  - configure java version
  - configure kotlin `jvmTarget` version
  - configure junit

- [app.plugin.android.common](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/android/AndroidCommonPlugin.kt)
  - configure `compileSdk`
  - configure `buildToolsVersion`
  - configure `minSdk`
  - configure java version

- [app.plugin.compose](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/compose/ComposePlugin.kt)
  - configure buildFeatures compose
  - configure `kotlinCompilerExtensionVersion`
  - add basic compose dependencies

- [app.plugin.hilt.android](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/hilt/HiltAndroidPlugin.kt)
  - configure Hilt Android plugin
  - configure KSP plugin
  - add Hilt Android dependencies

- [app.plugin.hilt.android.test](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/hilt/HiltAndroidTestPlugin.kt)
  - configure `testInstrumentationRunner`
  - add Hilt Android test dependencies

- [app.plugin.hilt.jvm](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/hilt/HiltJvmPlugin.kt)
  - configure KSP plugin
  - add Hilt core dependencies

- [app.plugin.room.android](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/room/RoomAndroidPlugin.kt)
  - configure KSP plugin
  - add room dependencies

- [app.plugin.mapstruct](convention/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/plugin/mapstruct/MapStructPlugin.kt)
  - add kapt plugin
  - add MapStruct dependencies
