package ch.app.hk.bank.locator.feature.onboarding.ui.permission.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun RequestLocationPermissionContent(
    modifier: Modifier = Modifier,
    onGrantPermission: () -> Unit,
    onSkip: () -> Unit,
) {
    Scaffold(modifier = modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding(),
                    )
                    .padding(horizontal = 16.dp)
                    .padding(
                        top = 55.dp,
                        bottom = 24.dp,
                    ),
        ) {
            RequestLocationPermissionContentHeader()

            val lottieResource =
                LottieCompositionSpec.RawRes(R.raw.onboarding_request_location_permission)
            val composition by rememberLottieComposition(lottieResource)
            LottieAnimation(
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .weight(1f)
                        .align(alignment = Alignment.CenterHorizontally),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )

            Button(
                modifier =
                    Modifier
                        .testTag(TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_BUTTON_GRANT)
                        .fillMaxWidth(),
                onClick = onGrantPermission,
            ) {
                Text(text = stringResource(id = R.string.onboarding_button_grant_permission))
            }

            OutlinedButton(
                modifier =
                    Modifier
                        .testTag(TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_BUTTON_SKIP)
                        .fillMaxWidth(),
                onClick = onSkip,
            ) {
                Text(text = stringResource(id = R.string.onboarding_button_skip))
            }
        }
    }
}

@Composable
private fun RequestLocationPermissionContentHeader() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.onboarding_title_enable_location),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall,
    )

    Text(
        modifier =
            Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
        text = stringResource(id = R.string.onboarding_subtitle_enable_location),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodySmall,
    )
}

internal const val TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_BUTTON_GRANT =
    "onboarding_request_location_permission_button_grant"
internal const val TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_BUTTON_SKIP =
    "onboarding_request_location_permission_button_skip"

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun RequestLocationPermissionContentPreviewDay() {
    AppTheme {
        RequestLocationPermissionController {}
    }
}

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.BLACK.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun RequestLocationPermissionContentPreviewNight() {
    AppTheme {
        RequestLocationPermissionController {}
    }
}
