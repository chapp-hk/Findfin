package ch.app.hk.bank.locator.core.location.launcher.permission

import ch.app.hk.bank.locator.core.location.impl.helper.permission.PermissionHelper
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationPermissionResultContract unit tests")
class LocationPermissionResultContractTest {
    private val mockPermissionHelper = mockk<PermissionHelper>()
    private val resultContract = LocationPermissionResultContract(mockPermissionHelper)

    @Test
    fun `test parseResult`() {
        every { mockPermissionHelper.checkPermission() } returns true
        resultContract.parseResult(0, null) shouldBe true

        every { mockPermissionHelper.checkPermission() } returns false
        resultContract.parseResult(0, null) shouldBe false
    }
}
