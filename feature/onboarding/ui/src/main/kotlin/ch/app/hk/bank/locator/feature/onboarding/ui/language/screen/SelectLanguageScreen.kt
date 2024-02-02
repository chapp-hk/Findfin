package ch.app.hk.bank.locator.feature.onboarding.ui.language.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel.SelectLanguageViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel.SelectLanguageViewModelImpl

@Composable
fun SelectLanguageScreen(
    selectLanguageViewModel: SelectLanguageViewModel = hiltViewModel<SelectLanguageViewModelImpl>(),
    goToRequestPermission: () -> Unit,
    content: @Composable () -> Unit = {
        SelectLanguageContent(
            availableLanguages = selectLanguageViewModel.availableLanguages,
            onLanguageSelect = { localeTag ->
                selectLanguageViewModel.setLanguage(localeTag)
            },
        )
    },
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
                text = stringResource(id = R.string.onboarding_title_language),
                style = MaterialTheme.typography.headlineSmall,
            )

            ScreenStateView(
                state = selectLanguageViewModel.uiState.collectAsStateWithLifecycle().value,
                loading = {
                    CircularProgressIndicator()
                },
                empty = {
                    content()
                },
                success = {
                    goToRequestPermission()
                },
            )
        }
    }
}
