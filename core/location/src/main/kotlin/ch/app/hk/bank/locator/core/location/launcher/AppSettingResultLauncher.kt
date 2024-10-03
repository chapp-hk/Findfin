package ch.app.hk.bank.locator.core.location.launcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.Composable

/**
 * Remembers a launcher for the app settings screen.
 *
 * @param onResult A callback invoked when the app settings screen is closed.
 * @return A launcher for the app settings screen.
 */
@Composable
fun rememberLauncherForAppSetting(onResult: (Unit) -> Unit) =
    rememberLauncherForActivityResult(
        contract = AppSettingResultContract(),
        onResult = onResult,
    )

/**
 * An [ActivityResultContract] for launching the app settings screen.
 */
private class AppSettingResultContract : ActivityResultContract<Unit, Unit>() {
    /**
     * Creates an intent to launch the app settings screen.
     *
     * @param context The context used to create the intent.
     * @param input The input data for the intent (not used in this contract).
     * @return An intent to launch the app settings screen.
     */
    override fun createIntent(
        context: Context,
        input: Unit,
    ) = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .setData(Uri.fromParts("package", context.packageName, null))

    /**
     * Parses the result of the app settings screen.
     *
     * @param resultCode The result code returned by the app settings screen.
     * @param intent The intent returned by the app settings screen.
     * @return The parsed result (always Unit in this contract).
     */
    override fun parseResult(
        resultCode: Int,
        intent: Intent?,
    ) = Unit
}
