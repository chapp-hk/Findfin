package ch.app.hk.bank.locator.feature.home.ui.nearby.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByItemUiModel

@Composable
internal fun ServiceItem(service: NearByItemUiModel) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "${service.name} - ${service.address}",
        )
    }
}
