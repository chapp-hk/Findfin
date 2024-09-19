package ch.app.hk.bank.locator.core.preferences.ui.foundation

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.preferences.runtime.PreferenceStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun ListPreference(
    title: String,
    list: List<ListPreferenceItem>,
    preferenceStore: PreferenceStore<String>,
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedValue by preferenceStore.get().collectAsStateWithLifecycle(initialValue = null)
    var isShowDialog by remember { mutableStateOf(false) }

    ListItem(
        modifier = Modifier.clickable { isShowDialog = true },
        headlineContent = { Text(text = title) },
        supportingContent = selectedValue?.let { { Text(text = it) } },
    )

    if (isShowDialog) {
        ListPreferenceDialog(
            title = title,
            list = list,
            onItemSelected = { key ->
                coroutineScope.launch {
                    preferenceStore.set(key)
                }
            },
            onDismissRequest = { isShowDialog = false },
        )
    }
}

@Composable
private fun ListPreferenceDialog(
    title: String,
    list: List<ListPreferenceItem>,
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
                            .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                )

                list.forEach { item ->
                    ListItem(
                        modifier =
                            Modifier.clickable {
                                onItemSelected(item.key)
                                onDismissRequest()
                            },
                        headlineContent = { Text(text = item.title) },
                        leadingContent = {
                            RadioButton(
                                selected = false,
                                onClick = null,
                            )
                        },
                    )
                }
            }
        }
    }
}

data class ListPreferenceItem(
    val title: String,
    val key: String,
)

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
                    title = "Item 1",
                    key = "item1",
                ),
                ListPreferenceItem(
                    title = "Item 2",
                    key = "item2",
                ),
            ),
        preferenceStore =
            object : PreferenceStore<String> {
                override val key: String = "key"

                override fun get(): Flow<String> = flowOf("item1")

                override suspend fun set(value: String) {}
            },
    )
}
