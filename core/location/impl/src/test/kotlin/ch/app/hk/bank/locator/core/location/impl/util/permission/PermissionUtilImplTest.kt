package ch.app.hk.bank.locator.core.location.impl.util.permission

import android.content.Context
import android.content.pm.PackageManager
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("PermissionRepositoryImpl unit tests")
class PermissionUtilImplTest {
    private val context = mockk<Context>()
    private val permissionRepository = PermissionUtilImpl(context)

    @Test
    fun `checkPermission returns true when permission is granted`() {
        every { context.checkSelfPermission(any()) } returns PackageManager.PERMISSION_GRANTED

        val result = permissionRepository.checkPermission()

        result shouldBe true
    }

    @Test
    fun `checkPermission returns false when permission is denied`() {
        every { context.checkSelfPermission(any()) } returns PackageManager.PERMISSION_DENIED

        val result = permissionRepository.checkPermission()

        result shouldBe false
    }
}
