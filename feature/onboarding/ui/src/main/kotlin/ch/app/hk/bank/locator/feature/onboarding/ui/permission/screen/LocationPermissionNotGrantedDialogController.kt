package ch.app.hk.bank.locator.feature.onboarding.ui.permission.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import ch.app.hk.bank.locator.feature.onboarding.ui.R

@Composable
fun locationPermissionNotGrantedDialogController(onConfirm: () -> Unit): MutableState<Boolean> {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
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
                        showDialog.value = false
                        onConfirm()
                    },
                ) {
                    Text(text = stringResource(id = R.string.onboarding_button_ok))
                }
            },
        )
    }

    return showDialog
}
