package org.chapp.findfin.feature.locator.presentation.ui.map.model

import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.Position
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankType

class BankModelMapper {
    fun toMapMarker(model: BankModel): MapMarker<BankType> {
        return MapMarker(
            markerPosition = Position(latitude = model.latitude, longitude = model.longitude),
            markerTitle = model.address,
            type = BankType.valueOf(model.type.uppercase()),
        )
    }
}
