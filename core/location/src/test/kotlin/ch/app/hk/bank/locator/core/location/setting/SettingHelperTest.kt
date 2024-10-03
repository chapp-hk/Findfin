package ch.app.hk.bank.locator.core.location.setting

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.result.IntentSenderRequest
import androidx.core.content.ContextCompat
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskError
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskResult
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SettingHelperTest {
    private val context = mockk<Context>(relaxed = true)

    private val settingHelper = SettingHelper(context = context)

    @Test
    fun `getSettings returns NoSensor when device has no GPS sensor`() {
        every { context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) } returns false

        val result = settingHelper.getSettings()

        result shouldBe LocationSettingStatus.NoSensor
    }

    @Test
    fun `getSettings returns Disabled when GPS is not enabled`() {
        every { context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) } returns true
        every {
            ContextCompat.getSystemService(context, LocationManager::class.java)!!
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
        } returns false

        val result = settingHelper.getSettings()

        result shouldBe LocationSettingStatus.Disabled
    }

    @Test
    fun `getSettings returns Enabled when GPS is enabled`() {
        every { context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) } returns true
        every {
            ContextCompat.getSystemService(context, LocationManager::class.java)!!
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
        } returns true

        val result = settingHelper.getSettings()

        result shouldBe LocationSettingStatus.Enabled
    }

    @Test
    fun `getIntentSenderRequest returns null when settings are already enabled`() {
        runTest {
            mockkStatic(LocationServices::class) {
                val settingsClient = mockk<SettingsClient>()
                every { LocationServices.getSettingsClient(context) } returns settingsClient
                coEvery { settingsClient.checkLocationSettings(any()) } returns
                    mockTaskResult(mockk(relaxed = true))

                val result = settingHelper.getIntentSenderRequest()

                result shouldBe null
            }
        }
    }

    @Test
    fun `getIntentSenderRequest returns IntentSenderRequest when settings need to be enabled`() {
        runTest {
            mockkStatic(LocationServices::class) {
                val settingsClient = mockk<SettingsClient>()
                every { LocationServices.getSettingsClient(context) } returns settingsClient
                coEvery { settingsClient.checkLocationSettings(any()) } returns
                    mockTaskResult(mockk(relaxed = true))

                val resolvableApiException =
                    mockk<ResolvableApiException> {
                        every { resolution } returns mockk(relaxed = true)
                    }

                coEvery { settingsClient.checkLocationSettings(any()) } returns
                    mockTaskError(resolvableApiException)

                val result = settingHelper.getIntentSenderRequest()

                result.shouldBeInstanceOf<IntentSenderRequest>()
            }
        }
    }
}
