package org.chapp.findfin.core.preferences.ui.foundation

import android.content.res.Configuration
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Preference(
    title: String,
    description: String? = null,
) {
    ListItem(
        headlineContent = { Text(text = title) },
        supportingContent = description?.let { { Text(text = it) } },
    )
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
private fun PreferenceItemPreview() {
    Preference(
        title = "Title",
        description = "Description",
    )
}
