package ch.app.hk.bank.locator.core.location.state.helper.setting

import androidx.activity.result.IntentSenderRequest
import ch.app.hk.bank.locator.core.location.state.LocationSettingResult
import ch.app.hk.bank.locator.core.location.state.helper.gps.GpsHelper
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskError
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskResult
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.SettingsClient
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class SettingHelperTest {
    private val gpsHelper = mockk<GpsHelper>()
    private val settingsClient = mockk<SettingsClient>()

    private val settingHelper =
        SettingHelper(
            gpsHelper = gpsHelper,
            settingsClient = settingsClient,
        )

    @Test
    fun `getSettings returns NoSensor when no GPS sensor`() {
        every { gpsHelper.hasGpsSensor() } returns false

        val result = settingHelper.getSettings()

        result shouldBe LocationSettingResult.NoSensor
    }

    @Test
    fun `getSettings returns Enabled when GPS is enabled`() {
        every { gpsHelper.hasGpsSensor() } returns true
        every { gpsHelper.isGpsEnabled() } returns true

        val result = settingHelper.getSettings()

        result shouldBe LocationSettingResult.Enabled
    }

    @Test
    fun `getSettings returns Disabled when GPS is disabled`() {
        every { gpsHelper.hasGpsSensor() } returns true
        every { gpsHelper.isGpsEnabled() } returns false

        val result = settingHelper.getSettings()

        result shouldBe LocationSettingResult.Disabled
    }

    @Test
    fun `getIntentSenderRequest returns null when settings are satisfied`() {
        runTest {
            coEvery { settingsClient.checkLocationSettings(any()) } returns mockTaskResult(mockk())

            val result = settingHelper.getIntentSenderRequest()

            result shouldBe null
        }
    }

    @Test
    fun `getIntentSenderRequest returns IntentSenderRequest when ResolvableApiException is thrown`() {
        runTest {
            val resolvableApiException =
                mockk<ResolvableApiException> {
                    every { resolution } returns mockk()
                    every { resolution.intentSender } returns mockk()
                }

            coEvery { settingsClient.checkLocationSettings(any()) } returns
                mockTaskError(resolvableApiException)

            val result = settingHelper.getIntentSenderRequest()

            result.shouldBeInstanceOf<IntentSenderRequest>()
        }
    }

    @Test
    fun `getIntentSenderRequest returns null when non-ResolvableApiException is thrown`() {
        runTest {
            coEvery { settingsClient.checkLocationSettings(any()) } returns mockTaskError(Exception())

            val result = settingHelper.getIntentSenderRequest()

            result shouldBe null
        }
    }
}
