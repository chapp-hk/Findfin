package ch.app.hk.bank.locator.feature.onboarding.ui.language.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel.SelectLanguageViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel.SelectLanguageViewModelImpl
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SelectLanguageScreen(
    modifier: Modifier = Modifier,
    goToRequestPermission: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
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
                    ),
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.onboarding_title_language),
                style = MaterialTheme.typography.headlineSmall,
            )

            val lottieResource =
                LottieCompositionSpec.RawRes(R.raw.onboarding_select_language_loading)
            val composition by rememberLottieComposition(lottieResource)
            LottieAnimation(
                modifier =
                    Modifier
                        .fillMaxSize(fraction = 0.5f)
                        .align(alignment = Alignment.CenterHorizontally),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )

            SelectLanguageScreenStateView(goToRequestPermission = goToRequestPermission)
        }
    }
}

@Composable
private fun SelectLanguageScreenStateView(
    selectLanguageViewModel: SelectLanguageViewModel = hiltViewModel<SelectLanguageViewModelImpl>(),
    goToRequestPermission: () -> Unit,
) {
    ScreenStateView(
        state = selectLanguageViewModel.uiState.collectAsStateWithLifecycle(),
        loading = {
            val loadingContentDescription =
                stringResource(id = R.string.onboarding_content_description_loading)

            CircularProgressIndicator(
                modifier =
                    Modifier
                        .semantics { contentDescription = loadingContentDescription }
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
            )
        },
        empty = {
            SelectLanguageContent(
                availableLanguages = selectLanguageViewModel.availableLanguages,
                onLanguageSelect = selectLanguageViewModel::setLanguage,
            )
        },
        error = { _, data ->
            SelectLanguageError {
                selectLanguageViewModel.setLanguage(data.selectedLanguageTag)
            }
        },
        success = {
            goToRequestPermission()
        },
    )
}
