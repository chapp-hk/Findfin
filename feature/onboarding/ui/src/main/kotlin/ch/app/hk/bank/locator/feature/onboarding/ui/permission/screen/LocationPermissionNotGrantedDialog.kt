package ch.app.hk.bank.locator.feature.onboarding.ui.permission.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.feature.onboarding.ui.R

@Composable
fun LocationPermissionNotGrantedDialog(
    isShowDialog: Boolean,
    onConfirm: () -> Unit,
) {
    if (isShowDialog) {
        AlertDialog(
            modifier = Modifier.testTag(tag = TEST_TAG_ONBOARDING_LOCATION_PERMISSION_NOT_GRANTED_DIALOG),
            title = {
                Text(text = stringResource(id = R.string.onboarding_title_permission_denied))
            },
            text = {
                Text(text = stringResource(id = R.string.onboarding_message_guide_location_permission_setting))
            },
            onDismissRequest = {},
            confirmButton = {
                TextButton(
                    modifier = Modifier.testTag(TEST_TAG_ONBOARDING_LOCATION_PERMISSION_NOT_GRANTED_DIALOG_BUTTON),
                    onClick = {
                        onConfirm()
                    },
                ) {
                    Text(text = stringResource(id = R.string.onboarding_button_ok))
                }
            },
        )
    }
}

internal const val TEST_TAG_ONBOARDING_LOCATION_PERMISSION_NOT_GRANTED_DIALOG =
    "onboarding_location_permission_not_granted_dialog"
internal const val TEST_TAG_ONBOARDING_LOCATION_PERMISSION_NOT_GRANTED_DIALOG_BUTTON =
    "onboarding_location_permission_not_granted_dialog_button"

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun LocationPermissionNotGrantedDialogPreviewDay() {
    AppTheme {
        LocationPermissionNotGrantedDialog(true) {}
    }
}

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.BLACK.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun LocationPermissionNotGrantedDialogPreviewNight() {
    AppTheme {
        LocationPermissionNotGrantedDialog(true) {}
    }
}
