package ch.app.hk.bank.locator.core.location.launcher.setting

import ch.app.hk.bank.locator.core.location.impl.helper.hardware.GpsHelper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationSourceSettingsResultContract unit tests")
class LocationSourceSettingsResultContractTest {
    @Test
    fun `test parseResult`() {
        val mockGpsHelper = mockk<GpsHelper>()
        val resultContract = LocationSourceSettingsResultContract(mockGpsHelper)

        every { mockGpsHelper.isGpsEnabled() } returns true
        assertTrue(resultContract.parseResult(0, null))

        every { mockGpsHelper.isGpsEnabled() } returns false
        assertFalse(resultContract.parseResult(0, null))
    }
}
