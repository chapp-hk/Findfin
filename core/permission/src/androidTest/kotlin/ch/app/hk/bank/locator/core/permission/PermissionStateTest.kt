package ch.app.hk.bank.locator.core.permission

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.rule.GrantPermissionRule
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test

class PermissionStateTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    @Test
    fun test_permission_isGranted() {
        composeTestRule.setContent {
            val state = rememberPermissionState(Manifest.permission.CAMERA)

            state.result.isGranted shouldBe true
            state.result.shouldShowRationale shouldBe false
        }
    }

    @Test
    fun test_permission_not_isGranted() {
        composeTestRule.setContent {
            val state = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

            state.result.isGranted shouldBe false
            state.result.shouldShowRationale shouldBe false
        }
    }
}
