package org.chapp.findfin.feature.locator.presentation.ui.map.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.chapp.findfin.core.design.ui.foundation.modifier.contentDescription
import org.chapp.findfin.core.design.ui.foundation.search.AppSearchBar
import org.chapp.findfin.core.design.ui.foundation.search.rememberAppSearchBarState
import org.chapp.findfin.core.map.AppMap
import org.chapp.findfin.core.map.Position
import org.chapp.findfin.core.map.rememberAppMapCameraState
import org.chapp.findfin.feature.locator.presentation.R
import org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel.MapViewModel

@Composable
internal fun MapScreen(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
) {
    Scaffold(modifier = modifier) { innerPadding ->
        val searchBarState =
            rememberAppSearchBarState(
                placeholder = stringResource(id = R.string.locator_tab_map),
            )

        AppSearchBar(
            modifier =
                Modifier
                    .alpha(0.5f)
                    .padding(innerPadding)
                    .padding(top = 16.dp)
                    .contentDescription(stringResource(id = R.string.locator_tab_map)),
            state = searchBarState,
            onSearch = {},
        )

        val mapCameraState =
            rememberAppMapCameraState(
                initPosition = Position(latitude = 22.3193, longitude = 114.1694),
                initZoom = 10f,
            )

        val mapMarkers by mapViewModel.uiState.collectAsStateWithLifecycle()

        AppMap(
            modifier = modifier,
            cameraState = mapCameraState,
            markers = mapMarkers,
        )
    }
}
