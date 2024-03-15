package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.onboarding.ui.R

@Composable
fun LocationPermissionNotGrantedDialog(
    isShowDialog: Boolean,
    onConfirm: () -> Unit,
) {
    if (isShowDialog) {
        AlertDialog(
            title = {
                Text(text = stringResource(id = R.string.onboarding_title_permission_denied))
            },
            text = {
                Text(text = stringResource(id = R.string.onboarding_message_guide_location_permission_setting))
            },
            onDismissRequest = {},
            confirmButton = {
                TextButton(
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

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun LocationPermissionNotGrantedDialogPreviewDay() {
    AppContent {
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
    AppContent {
        LocationPermissionNotGrantedDialog(true) {}
    }
}
