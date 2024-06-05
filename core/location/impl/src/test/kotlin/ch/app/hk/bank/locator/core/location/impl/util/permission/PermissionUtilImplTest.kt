package ch.app.hk.bank.locator.core.location.impl.util.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("PermissionRepositoryImpl unit tests")
class PermissionUtilImplTest {
    private val context = mockk<Context>()

    private val permissionUtil = PermissionUtilImpl(context)

    @BeforeEach
    fun setUp() {
        mockkStatic(ContextCompat::class)
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(ContextCompat::class)
    }

    @Test
    fun `checkPermission returns true when permission is granted`() {
        every { ContextCompat.checkSelfPermission(any(), any()) } returns PackageManager.PERMISSION_GRANTED

        val result = permissionUtil.checkPermission()

        result shouldBe true
    }

    @Test
    fun `checkPermission returns false when permission is denied`() {
        every { ContextCompat.checkSelfPermission(any(), any()) } returns PackageManager.PERMISSION_DENIED

        val result = permissionUtil.checkPermission()

        result shouldBe false
    }
}
