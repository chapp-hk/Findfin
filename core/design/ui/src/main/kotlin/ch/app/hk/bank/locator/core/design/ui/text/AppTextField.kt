package ch.app.hk.bank.locator.core.design.ui.text

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
import ch.app.hk.bank.locator.core.design.ui.AppContent

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
            if (state.isClearErrorWhenInput) {
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
@Composable
private fun AppTextFieldPreviewDayMode(
    @PreviewParameter(AppTextFieldPreviewParameterProvider::class) state: AppTextFieldState,
) {
    AppContent {
        AppTextField(state = state)
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = Color.BLACK.toLong(),
)
@Composable
private fun AppTextFieldPreviewNightMode(
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
                override val isClearErrorWhenInput: Boolean = true

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
                override val isClearErrorWhenInput: Boolean = true

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
                override val isClearErrorWhenInput: Boolean = true

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
                override val isClearErrorWhenInput: Boolean = true

                override fun setErrorText(errorText: String) {
                    // no implementation
                }

                override fun clearErrorText() {
                    // no implementation
                }
            },
        )
}
