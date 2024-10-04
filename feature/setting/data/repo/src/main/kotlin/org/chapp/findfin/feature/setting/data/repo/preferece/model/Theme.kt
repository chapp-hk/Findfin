package org.chapp.findfin.feature.setting.data.repo.preferece.model

/**
 * Enum class representing different themes available in the application.
 *
 * @property themeValue The string representation of the theme.
 */
enum class Theme(val themeValue: String) {
    /**
     * Light theme.
     */
    LIGHT("LIGHT"),

    /**
     * Dark theme.
     */
    DARK("DARK"),

    /**
     * System default theme.
     */
    SYSTEM("SYSTEM"),
    ;

    companion object {
        /**
         * Returns the [Theme] corresponding to the given string value.
         *
         * @param value The string representation of the theme.
         * @return The corresponding [Theme] or [SYSTEM] if the value is null or does not match any theme.
         */
        fun fromValue(value: String?): Theme {
            return when (value) {
                LIGHT.themeValue -> LIGHT
                DARK.themeValue -> DARK
                else -> SYSTEM
            }
        }
    }
}
