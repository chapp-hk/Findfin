package ch.app.hk.bank.locator.feature.auth.ui.introduction.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.feature.auth.ui.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthIntroductionScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = {},
                actions = {
                    TextButton(onClick = { }) {
                        Text(text = "Skip")
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 24.dp,
                    ),
        ) {
            val lottieResource =
                LottieCompositionSpec.RawRes(R.raw.auth_introduction)
            val composition by rememberLottieComposition(lottieResource)
            LottieAnimation(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .align(alignment = Alignment.CenterHorizontally),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )

            Button(
                modifier =
                    Modifier
                        // .testTag(TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_BUTTON_GRANT)
                        .fillMaxWidth(),
                onClick = {},
            ) {
                Text(text = "Login")
            }

            OutlinedButton(
                modifier =
                    Modifier
//                    .testTag(TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_BUTTON_SKIP)
                        .fillMaxWidth(),
                onClick = {},
            ) {
                Text(text = "Register")
            }
        }
    }
}

@Preview
@Composable
private fun AuthIntroductionScreenPreview() {
    AppTheme {
        AuthIntroductionScreen()
    }
}
