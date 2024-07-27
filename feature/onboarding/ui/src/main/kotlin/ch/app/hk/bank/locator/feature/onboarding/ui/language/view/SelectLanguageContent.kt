package ch.app.hk.bank.locator.feature.onboarding.ui.language.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.onboarding.ui.R
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel

@Composable
internal fun SelectLanguageContent(
    modifier: Modifier = Modifier,
    availableLanguages: List<SelectLanguageUiModel>,
    onLanguageSelect: (String) -> Unit,
) {
    val languageListContentDescription =
        stringResource(id = R.string.onboarding_content_description_language_list)

    LazyColumn(
        modifier =
            modifier
                .semantics { contentDescription = languageListContentDescription }
                .padding(top = 16.dp),
    ) {
        items(
            items = availableLanguages,
            key = { it.tag },
        ) { language ->
            OutlinedButton(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                onClick = { onLanguageSelect(language.tag) },
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .wrapContentHeight(),
                    text = language.displayName,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            HorizontalDivider(
                color = Color.Transparent,
                thickness = 8.dp,
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.BLACK.toLong(),
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SelectLanguageContentPreview(
    @PreviewParameter(AvailableLanguagesParameterProvider::class)
    availableLanguages: List<SelectLanguageUiModel>,
) {
    AppContent {
        SelectLanguageContent(
            availableLanguages = availableLanguages,
            onLanguageSelect = {},
        )
    }
}

private class AvailableLanguagesParameterProvider :
    PreviewParameterProvider<List<SelectLanguageUiModel>> {
    override val values: Sequence<List<SelectLanguageUiModel>> =
        sequenceOf(
            listOf(
                SelectLanguageUiModel(
                    displayName = "English",
                    tag = "en",
                ),
                SelectLanguageUiModel(
                    displayName = "Chinese",
                    tag = "zh",
                ),
            ),
        )
}
