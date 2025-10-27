package org.chapp.findfin.feature.onboarding.presentation.ui.language.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import org.chapp.findfin.core.design.ui.foundation.modifier.contentDescription
import org.chapp.findfin.feature.onboarding.presentation.R
import org.chapp.findfin.feature.onboarding.presentation.ui.language.state.SelectLanguageUiState
import org.chapp.findfin.feature.onboarding.presentation.ui.language.viewmodel.SelectLanguageViewModel

@Composable
internal fun SelectLanguageScreen(
    modifier: Modifier = Modifier,
    onFinishSelectLanguage: () -> Unit,
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

            SelectLanguageScreenStateView(onFinishSelectLanguage = onFinishSelectLanguage)
        }
    }
}

@Composable
private fun SelectLanguageScreenStateView(
    selectLanguageViewModel: SelectLanguageViewModel = hiltViewModel(),
    onFinishSelectLanguage: () -> Unit,
) {
    val state by selectLanguageViewModel.uiState.collectAsStateWithLifecycle()
    when (val value = state) {
        SelectLanguageUiState.Initial -> {
            SelectLanguageContent(
                availableLanguages = selectLanguageViewModel.availableLanguages,
                onLanguageSelect = selectLanguageViewModel::setLanguage,
            )
        }

        SelectLanguageUiState.Loading -> {
            val loadingContentDescription =
                stringResource(id = R.string.onboarding_content_description_loading)

            CircularProgressIndicator(
                modifier =
                    Modifier
                        .contentDescription(loadingContentDescription)
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
            )
        }

        is SelectLanguageUiState.Error -> {
            SelectLanguageError {
                selectLanguageViewModel.setLanguage(value.selectedLanguageTag)
            }
        }

        is SelectLanguageUiState.Success -> {
            LaunchedEffect(Unit) {
                onFinishSelectLanguage()
            }
        }
    }
}
