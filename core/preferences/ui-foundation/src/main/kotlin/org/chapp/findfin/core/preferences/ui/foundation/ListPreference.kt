package org.chapp.findfin.core.preferences.ui.foundation

import android.content.res.Configuration
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * A composable function that displays a list preference item with a dialog for selection.
 *
 * @param title The title of the preference item.
 * @param list The list of preference items to display in the dialog.
 * @param selectedValue A lambda function that returns the currently selected value.
 * @param onChange A lambda function to be called when a new item is selected.
 */
@Composable
fun <T> ListPreference(
    title: String,
    list: List<ListPreferenceItem<T>>,
    selectedValue: () -> T,
    onChange: (T) -> Unit,
) {
    val summaryText by remember {
        derivedStateOf { list.find { it.value == selectedValue() }?.title }
    }

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
            selectedItemKey = selectedValue(),
            onItemSelected = onChange,
            onDismissRequest = { isShowDialog = false },
        )
    }
}

/**
 * Data class representing an item in a list preference.
 *
 * @property title The title string for the preference item.
 * @property value The value associated with the preference item.
 */
data class ListPreferenceItem<T>(
    val title: String,
    val value: T,
)

/**
 * A composable function that displays a dialog for selecting a list preference item.
 *
 * @param title The title of the dialog.
 * @param list The list of preference items to display in the dialog.
 * @param selectedItemKey The currently selected item key.
 * @param onItemSelected A lambda function to be called when a new item is selected.
 * @param onDismissRequest A lambda function to be called when the dialog is dismissed.
 */
@Composable
private fun <T> ListPreferenceDialog(
    title: String,
    list: List<ListPreferenceItem<T>>,
    selectedItemKey: T?,
    onItemSelected: (T) -> Unit,
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
                        headlineContent = { Text(text = item.title) },
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
                    title = "Title 1",
                    value = "item1",
                ),
                ListPreferenceItem(
                    title = "Title 2",
                    value = "item2",
                ),
            ),
        selectedValue = { "item1" },
        onChange = { },
    )
}
