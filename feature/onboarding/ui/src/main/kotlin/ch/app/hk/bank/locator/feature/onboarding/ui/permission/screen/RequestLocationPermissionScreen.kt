package ch.app.hk.bank.locator.feature.onboarding.ui.permission.screen

import android.Manifest
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun RequestLocationPermissionScreen(goToHome: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        val showDialog = remember { mutableStateOf(false) }
        if (showDialog.value) {
            AlertDialog(
                title = {
                    Text(text = stringResource(id = R.string.onboarding_title_permission_denied))
                },
                text = {
                    Text(text = stringResource(id = R.string.onboarding_message_guide_location_permission_setting))
                },
                onDismissRequest = {},
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog.value = false
                            goToHome()
                        },
                    ) {
                        Text(text = stringResource(id = R.string.onboarding_button_ok))
                    }
                },
            )
        }

        val launcher =
            rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission(),
            ) { isGranted: Boolean ->
                if (isGranted) {
                    goToHome()
                } else {
                    showDialog.value = true
                }
            }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding(),
                    )
                    .padding(
                        top = 55.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 24.dp,
                    ),
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.onboarding_title_enable_location),
                style = MaterialTheme.typography.headlineSmall,
            )

            Text(
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.onboarding_subtitle_enable_location),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall,
            )

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
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
            ) {
                Text(text = stringResource(id = R.string.onboarding_button_grant_permission))
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    showDialog.value = true
                },
            ) {
                Text(text = stringResource(id = R.string.onboarding_button_skip))
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun RequestLocationPermissionScreenPreviewDay() {
    AppTheme {
        RequestLocationPermissionScreen {}
    }
}

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.BLACK.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun RequestLocationPermissionScreenPreviewNight() {
    AppTheme {
        RequestLocationPermissionScreen {}
    }
}
