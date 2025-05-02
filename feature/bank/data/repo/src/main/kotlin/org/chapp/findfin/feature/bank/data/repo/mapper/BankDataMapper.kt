package org.chapp.findfin.feature.bank.data.repo.mapper

import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemote
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.TypePath
import org.chapp.findfin.feature.bank.data.repo.model.BankModel

fun BankRemote.toBankLocal(
    language: String,
    type: TypePath,
): BankLocal {
    return BankLocal(
        language = language,
        type = type.name,
        district = this.district.replaceMappings(districtMappings),
        bankName = this.bankName.replaceMappings(bankNameMappings),
        typeName = this.typeName,
        address = this.address,
        serviceHours = this.serviceHours,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}

fun BankLocal.toBankModel(): BankModel {
    return BankModel(
        type = this.type,
        district = this.district,
        bankName = this.bankName,
        typeName = this.typeName,
        address = this.address,
        serviceHours = this.serviceHours,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}

private fun String.replaceMappings(mappings: Map<String, String>): String {
    val result = StringBuilder(this)
    mappings.forEach { (oldValue, newValue) ->
        var index = result.indexOf(oldValue, ignoreCase = true)
        while (index != -1) {
            result.replace(index, index + oldValue.length, newValue)
            index = result.indexOf(oldValue, index + newValue.length, ignoreCase = true)
        }
    }
    return result.toString().trim()
}
