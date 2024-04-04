package ch.app.hk.bank.locator.core.design.ui.text

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
import org.mapstruct.factory.Mappers

@Stable
interface AppTextFieldState {
    var value: String
    var placeholder: String
    var supportingText: String
    var isError: Boolean
    val isClearErrorWhenInput: Boolean

    fun setErrorText(errorText: String)

    fun clearErrorText()
}

@Composable
fun rememberAppTextFieldState(
    value: String = "",
    placeholder: String = "",
    supportingText: String = "",
    isError: Boolean = false,
    isClearErrorWhenInput: Boolean = false,
) = rememberSaveable(saver = appTextFieldStateSaver) {
    AppTextFieldStateImpl(
        value = value,
        placeholder = placeholder,
        supportingText = supportingText,
        isError = isError,
        isClearErrorWhenInput = isClearErrorWhenInput,
    )
}

private class AppTextFieldStateImpl(
    value: String,
    placeholder: String,
    supportingText: String,
    isError: Boolean,
    override val isClearErrorWhenInput: Boolean = true,
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

@Parcelize
private data class AppTextFieldStateSaveable(
    val value: String,
    val placeholder: String,
    val supportingText: String,
    val isError: Boolean,
    val isClearErrorWhenInput: Boolean = true,
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

@Mapper
private interface AppTextFieldStateMapper {
    fun toSaveable(state: AppTextFieldState): AppTextFieldStateSaveable

    fun toState(saveable: AppTextFieldStateSaveable): AppTextFieldStateImpl
}
