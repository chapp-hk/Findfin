package org.chapp.findfin.core.design.ui.foundation.text

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.parcelize.Parcelize
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * A stable interface representing the state of the AppTextField.
 */
@Stable
interface AppTextFieldState {
    /**
     * The current value of the text field.
     */
    var value: String

    /**
     * The placeholder text to be displayed in the text field.
     */
    var placeholder: String

    /**
     * The supporting text to be displayed below the text field.
     */
    var supportingText: String

    /**
     * Indicates whether the text field is in an error state.
     */
    var isError: Boolean

    /**
     * Sets the error text and marks the text field as having an error.
     *
     * @param errorText The error text to be displayed.
     */
    fun setErrorText(errorText: String)

    /**
     * Clears the error text and marks the text field as not having an error.
     */
    fun clearErrorText()
}

/**
 * A composable function that remembers the state of the AppTextField.
 *
 * @param value The initial value of the text field.
 * @param placeholder The initial placeholder text to be displayed in the text field.
 * @param supportingText The initial supporting text to be displayed below the text field.
 * @param isError The initial error state of the text field.
 * @return An instance of [AppTextFieldState] with the remembered state.
 */
@Composable
fun rememberAppTextFieldState(
    value: String = "",
    placeholder: String = "",
    supportingText: String = "",
    isError: Boolean = false,
) = rememberSaveable(saver = appTextFieldStateSaver) {
    AppTextFieldStateImpl(
        value = value,
        placeholder = placeholder,
        supportingText = supportingText,
        isError = isError,
    )
}

private class AppTextFieldStateImpl(
    value: String,
    placeholder: String,
    supportingText: String,
    isError: Boolean,
) : AppTextFieldState {
    override var value by mutableStateOf(value)
    override var placeholder by mutableStateOf(placeholder)
    override var supportingText by mutableStateOf(supportingText)
    override var isError by mutableStateOf(isError)

    override fun setErrorText(errorText: String) {
        supportingText = errorText
        isError = true
    }

    override fun clearErrorText() {
        supportingText = ""
        isError = false
    }
}

/**
 * A Parcelable data class used to save and restore the state of the AppTextField.
 *
 * @property value The current value of the text field.
 * @property placeholder The placeholder text to be displayed in the text field.
 * @property supportingText The supporting text to be displayed below the text field.
 * @property isError Indicates whether the text field is in an error state.
 */
@Parcelize
private data class AppTextFieldStateSaveable(
    val value: String,
    val placeholder: String,
    val supportingText: String,
    val isError: Boolean,
) : Parcelable

private val appTextFieldStateSaver =
    Saver<AppTextFieldState, AppTextFieldStateSaveable>(
        save = { state ->
            Mappers.getMapper(AppTextFieldStateMapper::class.java).toSaveable(state)
        },
        restore = { saveable ->
            Mappers.getMapper(AppTextFieldStateMapper::class.java).toState(saveable)
        },
    )

/**
 * A mapper interface for converting between [AppTextFieldState] and [AppTextFieldStateSaveable].
 */
@Mapper
private interface AppTextFieldStateMapper {
    @Mapping(source = "error", target = "isError")
    fun toSaveable(state: AppTextFieldState): AppTextFieldStateSaveable

    @Mapping(source = "error", target = "isError")
    fun toState(saveable: AppTextFieldStateSaveable): AppTextFieldStateImpl
}
