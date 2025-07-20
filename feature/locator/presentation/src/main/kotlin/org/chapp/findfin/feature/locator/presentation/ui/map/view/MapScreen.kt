package org.chapp.findfin.feature.locator.presentation.ui.map.view

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.chapp.findfin.core.map.AppMap
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.Position
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.findfin.feature.locator.presentation.R
import org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel.MapViewModel

@Composable
internal fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
) {
    val mapMarkers by mapViewModel.uiState.collectAsStateWithLifecycle()

    AppMap(
        modifier = modifier,
        initPosition = Position(latitude = 22.3193, longitude = 114.1694),
        initZoom = 10f,
        markers = mapMarkers,
        onBoundsChange = { bounds ->
            mapViewModel.getBanksWithinBound(bounds)
        },
        markerContent = { marker: MapMarker<BankType> ->
            when (marker.type) {
                BankType.ATM -> {
                    Icon(
                        painter = painterResource(id = R.drawable.locator_ic_atm_marker),
                        contentDescription = "ATM at ${marker.markerTitle}",
                        modifier = Modifier.size(36.dp),
                        tint = Color.Unspecified,
                    )
                }
                BankType.BRANCH -> {
                    Icon(
                        painter = painterResource(id = R.drawable.locator_ic_bank_marker),
                        contentDescription = "Bank branch at ${marker.markerTitle}",
                        modifier = Modifier.size(36.dp),
                        tint = Color.Unspecified,
                    )
                }
            }
        },
    )
}
