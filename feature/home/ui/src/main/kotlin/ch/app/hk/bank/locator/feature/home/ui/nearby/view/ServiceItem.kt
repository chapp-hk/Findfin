package ch.app.hk.bank.locator.feature.home.ui.nearby.view

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByItemUiModel

@Composable
internal fun ServiceItem(service: NearByItemUiModel) {
    val contentDescription = stringResource(id = R.string.home_content_description_nearby_service)

    Column(
        modifier =
            Modifier
                .semantics { this.contentDescription = contentDescription }
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(
            text = service.name,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = service.address,
            color = MaterialTheme.colorScheme.onSurface,
        )
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
private fun ServiceItemPreview() {
    AppContent {
        ServiceItem(
            NearByItemUiModel(
                name = "Bank of China",
                address = "1/F, Bank of China Tower, 1 Garden Road, Central, Hong Kong",
                isFavourite = false,
            ),
        )
    }
}
