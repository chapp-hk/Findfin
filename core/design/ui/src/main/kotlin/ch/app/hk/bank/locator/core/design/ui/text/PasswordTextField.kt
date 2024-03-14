package ch.app.hk.bank.locator.core.design.ui.text

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.R

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String = "",
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    val icon =
        if (passwordVisibility) {
            painterResource(id = R.drawable.core_ui_ic_visibility_off)
        } else {
            painterResource(id = R.drawable.core_ui_ic_visibility)
        }

    val passwordVisualTransformation =
        if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }

    AppTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        placeholder = placeHolder,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisibility = !passwordVisibility },
            ) {
                Icon(
                    painter = icon,
                    contentDescription =
                        stringResource(
                            id = R.string.core_ui_content_description_visibility_toggle,
                        ),
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = passwordVisualTransformation,
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PasswordTextFieldPreviewDayMode() {
    AppContent {
        PasswordTextField(
            value = "",
            onValueChange = {},
            placeHolder = "Input password",
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PasswordTextFieldPreviewNightMode() {
    AppContent {
        PasswordTextField(
            value = "",
            onValueChange = {},
            placeHolder = "Input password",
        )
    }
}
