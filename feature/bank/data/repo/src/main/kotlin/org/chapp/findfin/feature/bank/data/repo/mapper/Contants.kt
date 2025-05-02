package org.chapp.findfin.feature.bank.data.repo.mapper

/**
 * This file contains constants and mappings used for district and bank name transformations.
 *
 * The constants represent standardized district names in English and Chinese,
 * which are used to map various input strings to their corresponding standardized values.
 * The mappings are utilized in the `replaceMappings` function to replace input strings with their
 * standardized equivalents.
 *
 * Visibility:
 * - Constants are private and only accessible within this file.
 * - Mappings are marked as `internal` and are accessible within the same module.
 */

private const val DISTRICT_CENTRAL_WESTERN = "Central & Western"

// private const val DISTRICT_EASTERN = "Eastern"
private const val DISTRICT_ISLANDS = "Islands"
private const val DISTRICT_KOWLOON_CITY = "Kowloon City"
private const val DISTRICT_KWAI_TSING = "Kwai Tsing"
private const val DISTRICT_KWUN_TONG = "Kwun Tong"
private const val DISTRICT_NORTH = "North"
private const val DISTRICT_SAI_KUNG = "Sai Kung"
private const val DISTRICT_SHA_TIN = "Sha Tin"
private const val DISTRICT_SHAM_SHUI_PO = "Sham Shui Po"
private const val DISTRICT_TAI_PO = "Tai Po"

// private const val DISTRICT_SOUTHERN = "Southern"
private const val DISTRICT_TSUE_WAN = "Tsuen Wan"
private const val DISTRICT_TUEN_MUN = "Tuen Mun"
private const val DISTRICT_WAN_CHAI = "Wan Chai"
private const val DISTRICT_WONG_TAI_SIN = "Wong Tai Sin"
private const val DISTRICT_YAU_TSIM_MONG = "Yau Tsim Mong"
private const val DISTRICT_YUEN_LONG = "Yuen Long"
// private const val DISTRICT_CHINESE_CENTRAL_WESTERN = "中西區"
// private const val DISTRICT_CHINESE_EAST = "東區"
// private const val DISTRICT_CHINESE_SOUTH = "南區"
// private const val DISTRICT_CHINESE_WAN_CHAI = "灣仔區"
// private const val DISTRICT_CHINESE_KOWLOON_CITY = "九龍城區"
// private const val DISTRICT_CHINESE_YAU_TSIM_MONG = "油尖旺區"
// private const val DISTRICT_CHINESE_SHAM_SHUI_PO = "深水埗區"
// private const val DISTRICT_CHINESE_WONG_TAI_SIN = "黃大仙區"
// private const val DISTRICT_CHINESE_KWUN_TONG = "觀塘區"
// private const val DISTRICT_CHINESE_TAI_PO = "大埔區"
// private const val DISTRICT_CHINESE_YUEN_LONG = "元朗區"
// private const val DISTRICT_CHINESE_TUEN_MUN = "屯門區"
// private const val DISTRICT_CHINESE_NORTH = "北區"
// private const val DISTRICT_CHINESE_SAI_KUNG = "西貢區"
// private const val DISTRICT_CHINESE_SHA_TIN = "沙田區"
// private const val DISTRICT_CHINESE_TSUE_WAN = "荃灣區"
// private const val DISTRICT_CHINESE_KWAI_TSING = "葵青區"
// private const val DISTRICT_CHINESE_ISLANDS = "離島區"

/**
 * A map of district name variations to their standardized English and Chinese equivalents.
 *
 * This mapping is used to normalize district names from various input formats to a consistent
 * format. For example, "Central and Western" and "CentralNWestern" are both mapped to
 * "Central & Western".
 */
internal val districtMappings =
    mapOf(
        "District" to "",
        "Central and Western" to DISTRICT_CENTRAL_WESTERN,
        "CentralNWestern" to DISTRICT_CENTRAL_WESTERN,
        "Cheung Chau" to DISTRICT_ISLANDS,
        "Lamma Island" to DISTRICT_ISLANDS,
        "Lantau Island" to DISTRICT_ISLANDS,
        "Outlying Islands" to DISTRICT_ISLANDS,
        "Outlying Island" to DISTRICT_ISLANDS,
        "Peng Chau" to DISTRICT_ISLANDS,
        "KowloonCity" to DISTRICT_KOWLOON_CITY,
        "KwaiTsing" to DISTRICT_KWAI_TSING,
        "KwunTong" to DISTRICT_KWUN_TONG,
        "Northern" to DISTRICT_NORTH,
        "SaiKung" to DISTRICT_SAI_KUNG,
        "ShaTin" to DISTRICT_SHA_TIN,
        "ShamShuiPo" to DISTRICT_SHAM_SHUI_PO,
        "Shum Shui Po" to DISTRICT_SHAM_SHUI_PO,
        "TaiPo" to DISTRICT_TAI_PO,
        "TsuenWan" to DISTRICT_TSUE_WAN,
        "TuenMun" to DISTRICT_TUEN_MUN,
        "WanChai" to DISTRICT_WAN_CHAI,
        "WongTaiSin" to DISTRICT_WONG_TAI_SIN,
        "Yau Tsui Mong" to DISTRICT_YAU_TSIM_MONG,
        "YauTsimMong" to DISTRICT_YAU_TSIM_MONG,
        "YuenLong" to DISTRICT_YUEN_LONG,
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

/**
 * A map of bank name variations to their standardized equivalents.
 *
 * This mapping is used to normalize bank names by removing unnecessary characters or
 * formatting inconsistencies. For example, "CMB WING LUNG BANK" is mapped to "CMB Wing Lung Bank".
 */
internal val bankNameMappings =
    mapOf(
        "Limited" to "",
        "  " to " ",
        "," to "",
        "( " to "(",
        " )" to ")",
        "CMB WING LUNG BANK" to "CMB Wing Lung Bank",
        "有限公司" to "",
    )
