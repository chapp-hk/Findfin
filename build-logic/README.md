# Convention Plugins

Defines project-specific convention plugins, used to keep a single source of truth for common module configurations.

Plugin ID naming convention:
app.plugin.{function_name}

Current list of convention plugins:
- [app.plugin.ktlint](plugins/src/main/kotlin/ch/app/hk/bank/locator/buildlogic/ktlint/KtlintPlugin.kt)
  - apply [ktlint gradle plugin](https://github.com/JLLeitschuh/ktlint-gradle) to root project and all subprojects
  - configure `ktlintFormat` pre-commit git hook
