package org.chapp.findfin.core.preferences.ui.foundation

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.chapp.findfin.core.preferences.runtime.PreferenceStore

/**
 * A composable function that displays a list preference item with a dialog for selection.
 *
 * @param title The title of the preference item.
 * @param list The list of preference items to display in the dialog.
 * @param preferenceStore The preference store to manage the selected preference value.
 */
@Composable
fun ListPreference(
    title: String,
    list: List<ListPreferenceItem>,
    preferenceStore: PreferenceStore<String>,
) {
    val selectedValue by preferenceStore.get().collectAsStateWithLifecycle(initialValue = "")
    val summaryText =
        list.find { it.value == selectedValue }?.let { stringResource(id = it.titleRes) }

    val coroutineScope = rememberCoroutineScope()
    var isShowDialog by remember { mutableStateOf(false) }

    ListItem(
        modifier = Modifier.clickable { isShowDialog = true },
        headlineContent = { Text(text = title) },
        supportingContent = summaryText?.let { { Text(text = it) } },
    )

    if (isShowDialog) {
        ListPreferenceDialog(
            title = title,
            list = list,
            selectedItemKey = selectedValue,
            onItemSelected = { key ->
                coroutineScope.launch {
                    preferenceStore.set(key)
                }
            },
            onDismissRequest = { isShowDialog = false },
        )
    }
}

/**
 * Data class representing an item in a list preference.
 *
 * @property titleRes The resource ID of the title string for the preference item.
 * @property value The value associated with the preference item.
 */
data class ListPreferenceItem(
    @StringRes val titleRes: Int,
    val value: String,
)

@Composable
private fun ListPreferenceDialog(
    title: String,
    list: List<ListPreferenceItem>,
    selectedItemKey: String?,
    onItemSelected: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column {
                Text(
                    text = title,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                            .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )

                list.forEach { item ->
                    ListItem(
                        modifier =
                            Modifier.clickable {
                                onItemSelected(item.value)
                                onDismissRequest()
                            },
                        headlineContent = { Text(text = stringResource(id = item.titleRes)) },
                        leadingContent = {
                            RadioButton(
                                selected = item.value == selectedItemKey,
                                onClick = null,
                            )
                        },
                    )
                }
            }
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
private fun ListPreferenceItemPreview() {
    ListPreference(
        title = "Title",
        list =
            listOf(
                ListPreferenceItem(
                    titleRes = 0,
                    value = "item1",
                ),
                ListPreferenceItem(
                    titleRes = 0,
                    value = "item2",
                ),
            ),
        preferenceStore =
            object : PreferenceStore<String> {
                override fun get(): Flow<String> = flowOf("item1")

                override suspend fun set(value: String) {
                    // no implementation
                }
            },
    )
}
