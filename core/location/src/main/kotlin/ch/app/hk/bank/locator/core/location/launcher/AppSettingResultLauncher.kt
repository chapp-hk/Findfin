package ch.app.hk.bank.locator.core.location.launcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable

@Composable
fun rememberLauncherForAppSetting(onResult: (Unit) -> Unit) =
    rememberLauncherForActivityResult(
        contract = AppSettingResultContract(),
        onResult = onResult,
    )

internal class AppSettingResultContract : ActivityResultContract<Unit, Unit>() {
    override fun createIntent(
        context: Context,
        input: Unit,
    ) = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .setData(Uri.fromParts("package", context.packageName, null))

    override fun parseResult(
        resultCode: Int,
        intent: Intent?,
    ) = Unit
}
