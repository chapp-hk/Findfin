package org.chapp.findfin.feature.locator.presentation.ui.map.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.chapp.findfin.core.map.AppMap
import org.chapp.findfin.core.map.Position
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
    )
}
