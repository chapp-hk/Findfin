package org.chapp.findfin.core.design.ui.search

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.chapp.findfin.core.design.ui.AppContent
import org.chapp.findfin.core.design.ui.R
import org.chapp.findfin.core.design.ui.modifier.contentDescription

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
        inputField = {
            SearchBarDefaults
                .InputField(
                    query = state.value,
                    onQueryChange = { newValue -> state.value = newValue },
                    onSearch = onSearch,
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = {
                        val inputFieldContentDescription =
                            stringResource(id = R.string.core_ui_content_description_search_input_field)

                        Text(
                            modifier = Modifier.contentDescription(inputFieldContentDescription),
                            text = state.placeholder,
                        )
                    },
                    leadingIcon = {
                        val contentDescription =
                            stringResource(id = R.string.core_ui_content_description_search_icon)

                        Icon(
                            painter = painterResource(R.drawable.core_ui_ic_search),
                            contentDescription = contentDescription,
                        )
                    },
                )
        },
        expanded = false,
        onExpandedChange = {},
    ) {
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = Color.WHITE.toLong(),
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = Color.BLACK.toLong(),
)
@Composable
private fun PasswordTextFieldPreview() {
    AppContent {
        AppSearchBar()
    }
}
