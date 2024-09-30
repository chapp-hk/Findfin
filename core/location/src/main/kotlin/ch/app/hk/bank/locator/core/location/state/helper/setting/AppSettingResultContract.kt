package ch.app.hk.bank.locator.core.location.state.helper.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable

class AppSettingResultContract : ActivityResultContract<Unit, Unit>() {
    override fun createIntent(
        context: Context,
        input: Unit,
    ): Intent {
        return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", context.packageName, null))
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?,
    ) {
    }
}

@Composable
fun rememberLauncherForAppSetting(onResult: (Unit) -> Unit) = rememberLauncherForActivityResult(AppSettingResultContract(), onResult)
