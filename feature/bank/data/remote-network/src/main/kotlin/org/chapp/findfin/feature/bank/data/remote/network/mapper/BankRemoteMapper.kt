package org.chapp.findfin.feature.bank.data.remote.network.mapper

import org.chapp.findfin.feature.bank.data.remote.network.model.BankResponse
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemote

fun BankResponse.toBankRemote(): BankRemote {
    return BankRemote(
        district = this.district,
        bankName = this.bankName,
        typeName = this.typeName,
        address = this.address,
        serviceHours = this.serviceHours,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}
