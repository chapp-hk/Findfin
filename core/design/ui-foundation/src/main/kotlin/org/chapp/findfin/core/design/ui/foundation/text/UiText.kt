package org.chapp.findfin.core.design.ui.foundation.text

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * A sealed class representing different types of UI text.
 */
sealed class UiText {
    /**
     * Represents an actual string value.
     *
     * @property value The actual string value.
     */
    data class ActualString(val value: String) : UiText()

    /**
     * Represents a string resource.
     *
     * @property resId The resource ID of the string.
     */
    data class ResourceString(
        @param:StringRes val resId: Int,
    ) : UiText()
}

/**
 * Converts the [UiText] to a string.
 *
 * @param args Optional arguments for formatting the string resource.
 * @return The string representation of the [UiText].
 */
@Composable
fun UiText.asString(vararg args: Any): String {
    val context = LocalContext.current
    return when (this) {
        is UiText.ActualString -> value
        is UiText.ResourceString -> context.getString(resId, *args)
    }
}
