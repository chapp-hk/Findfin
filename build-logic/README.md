# Convention Plugins

Defines project-specific convention plugins, used to keep a single source of truth for common module configurations.

Plugin ID naming convention:
app.plugin.{function_name}

Current list of convention plugins:
- [app.plugin.ktlint](plugins/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/ktlint/KtlintPlugin.kt)
  - apply [ktlint gradle plugin](https://github.com/JLLeitschuh/ktlint-gradle) to root project and all subprojects
  - configure `ktlintFormat` pre-commit git hook
- [app.plugin.detekt](plugins/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/detekt/DetektPlugin.kt)
  - apply [detekt gradle plugin](https://github.com/detekt/detekt) to root project and all subprojects
- [app.plugin.android.common](plugins/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/android/AndroidCommonPlugin.kt)
  - configure `compileSdk`
  - configure `buildToolsVersion`
  - configure `minSdk`
  - configure java version
- [app.plugin.compose](plugins/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/compose/ComposePlugin.kt)
  - configure buildFeatures compose 
  - configure `kotlinCompilerExtensionVersion`
  - add basic compose dependencies