package org.chapp.findfin.feature.locator.presentation.ui.map.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
    var checked by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        AppMap(
            modifier = Modifier.fillMaxSize().navigationBarsPadding(),
            initPosition = Position(latitude = 22.3193, longitude = 114.1694),
            initZoom = 10f,
            markers = mapMarkers,
            onBoundsChange = { bounds ->
                mapViewModel.getBanksWithinBound(bounds)
            },
            markerContent = { marker: MapMarker<BankType> ->
                Icon(
                    painter = painterResource(id = R.drawable.locator_ic_map_pin),
                    contentDescription = marker.markerTitle,
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .padding(bottom = 80.dp),
        )
    }
}
