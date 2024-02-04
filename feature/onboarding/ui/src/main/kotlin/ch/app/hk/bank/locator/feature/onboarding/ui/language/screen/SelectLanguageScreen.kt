package ch.app.hk.bank.locator.feature.onboarding.ui.language.screen

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.feature.onboarding.ui.language.state.SelectLanguageUiState
import ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel.SelectLanguageViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel.SelectLanguageViewModelImpl
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SelectLanguageScreen(
    selectLanguageViewModel: SelectLanguageViewModel = hiltViewModel<SelectLanguageViewModelImpl>(),
    goToRequestPermission: () -> Unit,
    loading: @Composable () -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
        )
    },
    error: @Composable (SelectLanguageUiState) -> Unit = { data ->
        SelectLanguageError {
            selectLanguageViewModel.setLanguage(data.selectedLanguageTag)
        }
    },
    content: @Composable () -> Unit = {
        SelectLanguageContent(
            availableLanguages = selectLanguageViewModel.availableLanguages,
            onLanguageSelect = { localeTag ->
                selectLanguageViewModel.setLanguage(localeTag)
            },
        )
    },
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
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
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.onboarding_title_language),
                style = MaterialTheme.typography.headlineSmall,
            )

            val lottieResource =
                LottieCompositionSpec.RawRes(R.raw.onboarding_select_language_loading)
            val composition by rememberLottieComposition(lottieResource)
            LottieAnimation(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                    .align(alignment = Alignment.CenterHorizontally),
                composition = composition,
                iterations = LottieConstants.IterateForever,
            )

            ScreenStateView(
                state = selectLanguageViewModel.uiState.collectAsStateWithLifecycle().value,
                loading = loading,
                empty = content,
                error = { _, data -> error(data) },
                success = {
                    goToRequestPermission()
                },
            )
        }
    }
}
