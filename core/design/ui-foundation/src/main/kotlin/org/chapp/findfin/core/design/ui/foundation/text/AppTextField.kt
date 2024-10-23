package org.chapp.findfin.core.design.ui.foundation.text

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.chapp.findfin.core.design.ui.foundation.AppContent

/**
 * A composable function that displays a text field with customizable state, trailing icon, visual transformation, and keyboard options.
 *
 * @param modifier The modifier to be applied to the text field.
 * @param state The state of the text field, including the current value, placeholder, supporting text, and error state.
 * @param trailingIcon A composable function to display a trailing icon in the text field. If null, no trailing icon is displayed.
 * @param visualTransformation The visual transformation to be applied to the text field's input. Defaults to [VisualTransformation.None].
 * @param keyboardOptions The keyboard options to be applied to the text field. Defaults to [KeyboardOptions.Default].
 */
@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    state: AppTextFieldState = rememberAppTextFieldState(),
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    val supportingTextComposable: @Composable (() -> Unit)? =
        if (state.supportingText.isNotEmpty()) {
            @Composable {
                Text(text = state.supportingText)
            }
        } else {
            null
        }

    OutlinedTextField(
        modifier = modifier,
        value = state.value,
        onValueChange = {
            state.value = it
            if (state.isError) {
                state.clearErrorText()
            }
        },
        placeholder = { Text(text = state.placeholder) },
        trailingIcon = trailingIcon,
        supportingText = supportingTextComposable,
        isError = state.isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = Color.WHITE.toLong(),
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = Color.BLACK.toLong(),
)
@Composable
private fun AppTextFieldPreview(
    @PreviewParameter(AppTextFieldPreviewParameterProvider::class) state: AppTextFieldState,
) {
    AppContent {
        AppTextField(state = state)
    }
}

private class AppTextFieldPreviewParameterProvider : PreviewParameterProvider<AppTextFieldState> {
    override val values =
        sequenceOf(
            object : AppTextFieldState {
                override var value: String = "testing value"
                override var placeholder: String = ""
                override var supportingText: String = ""
                override var isError: Boolean = false

                override fun setErrorText(errorText: String) {
                    // no implementation
                }

                override fun clearErrorText() {
                    // no implementation
                }
            },
            object : AppTextFieldState {
                override var value: String = ""
                override var placeholder: String = "testing placeholder"
                override var supportingText: String = ""
                override var isError: Boolean = false

                override fun setErrorText(errorText: String) {
                    // no implementation
                }

                override fun clearErrorText() {
                    // no implementation
                }
            },
            object : AppTextFieldState {
                override var value: String = ""
                override var placeholder: String = ""
                override var supportingText: String = "supporting text"
                override var isError: Boolean = false

                override fun setErrorText(errorText: String) {
                    // no implementation
                }

                override fun clearErrorText() {
                    // no implementation
                }
            },
            object : AppTextFieldState {
                override var value: String = ""
                override var placeholder: String = ""
                override var supportingText: String = "error text"
                override var isError: Boolean = true

                override fun setErrorText(errorText: String) {
                    // no implementation
                }

                override fun clearErrorText() {
                    // no implementation
                }
            },
        )
}
