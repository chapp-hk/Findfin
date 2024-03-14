package ch.app.hk.bank.locator.core.design.ui.text

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ch.app.hk.bank.locator.core.design.ui.AppContent

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) = OutlinedTextField(
    modifier = modifier,
    value = value,
    onValueChange = onValueChange,
    placeholder = { Text(text = placeholder) },
    trailingIcon = trailingIcon,
    visualTransformation = visualTransformation,
    keyboardOptions = keyboardOptions,
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AppTextFieldPreviewDayMode() {
    AppContent {
        AppTextField(
            value = "",
            onValueChange = {},
            placeholder = "Placeholder",
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldPreviewNightMode() {
    AppContent {
        AppTextField(
            value = "",
            onValueChange = {},
            placeholder = "Placeholder",
        )
    }
}
