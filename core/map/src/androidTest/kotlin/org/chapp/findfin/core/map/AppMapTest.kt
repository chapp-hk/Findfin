package org.chapp.findfin.core.map

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test

class AppMapTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testAppMap_initPosition() {
        val mapCameraState =
            AppMapCameraState(
                cameraPositionState =
                    CameraPositionState(
                        position =
                            CameraPosition.fromLatLngZoom(
                                LatLng(22.3193, 114.1694),
                                10f,
                            ),
                    ),
            )

        composeTestRule.setContent {
            AppMap(cameraState = mapCameraState)
        }

        mapCameraState.position.latitude shouldBe 22.3193
        mapCameraState.position.longitude shouldBe 114.1694
        mapCameraState.zoom shouldBe 10f
    }
}
