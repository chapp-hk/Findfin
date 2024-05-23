package ch.app.hk.bank.locator.core.design.ui.search

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.core.design.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    state: AppSearchBarState = rememberAppSearchBarState(),
    onSearch: (String) -> Unit = {},
) {
    SearchBar(
        modifier =
            modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        query = state.value,
        onQueryChange = { newValue -> state.value = newValue },
        placeholder = {
            val inputFieldContentDescription =
                stringResource(id = R.string.core_ui_content_description_search_input_field)

            Text(
                modifier =
                    Modifier.semantics {
                        contentDescription = inputFieldContentDescription
                    },
                text = state.placeholder,
            )
        },
        onSearch = onSearch,
        active = false,
        onActiveChange = {},
        leadingIcon = {
            val contentDescription =
                stringResource(id = R.string.core_ui_content_description_search_icon)

            Icon(
                painter = painterResource(R.drawable.core_ui_ic_search),
                contentDescription = contentDescription,
            )
        },
        shape = MaterialTheme.shapes.extraSmall,
    ) {
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = Color.WHITE.toLong(),
)
@Composable
private fun PasswordTextFieldPreviewDayMode() {
    AppContent {
        AppSearchBar()
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = Color.BLACK.toLong(),
)
@Composable
private fun PasswordTextFieldPreviewNightMode() {
    AppContent {
        AppSearchBar()
    }
}
