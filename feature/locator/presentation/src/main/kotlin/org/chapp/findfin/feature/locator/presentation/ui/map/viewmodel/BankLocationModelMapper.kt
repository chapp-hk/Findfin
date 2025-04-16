package org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel

import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.Position
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationModel

class BankLocationModelMapper {
    fun toMapMarker(model: BankLocationModel): MapMarker {
        return MapMarker(
            itemPosition = Position(latitude = model.latitude, longitude = model.longitude),
            itemTitle = model.address,
            itemSnippet = "",
            itemZIndex = 0f,
        )
    }
}
