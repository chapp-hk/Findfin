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
        district = this.district.formatDistrict(),
        bankName = this.bankName.formatBankName(),
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

private fun String.formatDistrict(): String {
    val replacements =
        mapOf(
            "District" to "",
            "Central and Western" to "Central & Western",
            "CentralNWestern" to "Central & Western",
            "Cheung Chau" to "Islands",
            "Lamma Island" to "Islands",
            "Lantau Island" to "Islands",
            "Outlying Islands" to "Islands",
            "Outlying Island" to "Islands",
            "Peng Chau" to "Islands",
            "KowloonCity" to "Kowloon City",
            "KwaiTsing" to "Kwai Tsing",
            "KwunTong" to "Kwun Tong",
            "Northern" to "North",
            "SaiKung" to "Sai Kung",
            "ShaTin" to "Sha Tin",
            "ShamShuiPo" to "Sham Shui Po",
            "Shum Shui Po" to "Sham Shui Po",
            "TaiPo" to "Tai Po",
            "TsuenWan" to "Tsuen Wan",
            "TuenMun" to "Tuen Mun",
            "WanChai" to "Wan Chai",
            "WongTaiSin" to "Wong Tai Sin",
            "Yau Tsui Mong" to "Yau Tsim Mong",
            "YauTsimMong" to "Yau Tsim Mong",
            "YuenLong" to "Yuen Long",
            "區" to "",
            "南丫島" to "離島",
            "坪洲" to "離島",
            "大嶼山" to "離島",
            "/kuiqing" to "",
            "長洲" to "離島",
            "中西" to "中西區",
            "東" to "東區",
            "南" to "南區",
            "灣仔" to "灣仔區",
            "九龍城" to "九龍城區",
            "油尖旺" to "油尖旺區",
            "深水埗" to "深水埗區",
            "黃大仙" to "黃大仙區",
            "觀塘" to "觀塘區",
            "大埔" to "大埔區",
            "元朗" to "元朗區",
            "屯門" to "屯門區",
            "北" to "北區",
            "西貢" to "西貢區",
            "沙田" to "沙田區",
            "荃灣" to "荃灣區",
            "葵青" to "葵青區",
            "離島" to "離島區",
        )

    val result = StringBuilder(this)
    for ((oldValue, newValue) in replacements) {
        var index = result.indexOf(oldValue, ignoreCase = true)
        while (index != -1) {
            result.replace(index, index + oldValue.length, newValue)
            index = result.indexOf(oldValue, index + newValue.length, ignoreCase = true)
        }
    }
    return result.toString().trim()
}

private fun String.formatBankName(): String {
    return this
        .replace(oldValue = "Limited", newValue = "")
        .replace(oldValue = "  ", newValue = " ")
        .replace(oldValue = ",", newValue = "")
        .replace(oldValue = "( ", newValue = "(")
        .replace(oldValue = " )", newValue = ")")
        .replace(oldValue = "CMB WING LUNG BANK", newValue = "CMB Wing Lung Bank", ignoreCase = true)
        .replace(oldValue = "有限公司", "")
        .trim()
}
