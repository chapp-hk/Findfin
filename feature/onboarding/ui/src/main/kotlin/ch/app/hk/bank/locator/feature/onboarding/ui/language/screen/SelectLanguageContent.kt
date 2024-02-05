package ch.app.hk.bank.locator.feature.onboarding.ui.language.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel

@Composable
internal fun SelectLanguageContent(
    modifier: Modifier = Modifier,
    availableLanguages: List<SelectLanguageUiModel>,
    onLanguageSelect: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .testTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_CONTENT_LIST)
            .padding(top = 16.dp),
    ) {
        items(availableLanguages) { language ->
            OutlinedButton(
                modifier = Modifier
                    .testTag(TEST_TAG_ONBOARDING_SELECT_LANGUAGE_CONTENT_LIST_BUTTON)
                    .fillMaxWidth(),
                onClick = { onLanguageSelect(language.tag) },
                shape = RoundedCornerShape(10.dp),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .wrapContentHeight(),
                    text = language.displayName,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Divider(
                color = Color.Transparent,
                thickness = 8.dp,
            )
        }
    }
}

internal const val TEST_TAG_ONBOARDING_SELECT_LANGUAGE_CONTENT_LIST =
    "onboarding_select_language_content_list"
internal const val TEST_TAG_ONBOARDING_SELECT_LANGUAGE_CONTENT_LIST_BUTTON =
    "onboarding_select_language_content_list_button"
