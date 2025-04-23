package org.chapp.findfin.feature.bank.data.local.database.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity
import org.chapp.findfin.feature.bank.data.local.room.BankDatabase
import org.junit.Test

class BankDaoTest {
    private val context = InstrumentationRegistry.getInstrumentation().context

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun testInsertAll_withNoDuplicateId() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, BankDatabase::class.java).build()

            val list =
                listOf(
                    BankEntity(
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    BankEntity(
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                )

            database.bankDao.insertAll(list)

            database.query("SELECT * FROM locator", null).use {
                it.count shouldBe 2
            }
        }
    }

    @Test
    fun testInsertAll_withDuplicateId() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, BankDatabase::class.java).build()

            val list =
                listOf(
                    BankEntity(
                        id = 1,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    BankEntity(
                        id = 2,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                    BankEntity(
                        id = 1,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "Bank of East Asia",
                        typeName = "ATM",
                        address = "10 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                )

            database.bankDao.insertAll(list)

            database.bankDao.getAll() shouldBe
                listOf(
                    BankEntity(
                        id = 1,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "Bank of East Asia",
                        typeName = "ATM",
                        address = "10 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    BankEntity(
                        id = 2,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                )
        }
    }

    @Test
    fun testGetBanksWithinBound() {
        runTest(testDispatcher) {
            val database = createDatabaseFromAsset()

            database
                .bankDao
                .getBanksWithinBound(
                    language = "en",
                    minLat = 22.294630813707222,
                    maxLat = 22.312641530083468,
                    minLon = 114.22859313885354,
                    maxLon = 114.24806027679244,
                ).size shouldBe 40
        }
    }

    @Test
    fun testGetDistinctBankNames() {
        runTest(testDispatcher) {
            val database = createDatabaseFromAsset()

            database.bankDao.getDistinctBankNames(language = "en") shouldBe
                listOf(
                    "Bank of  Communications (Hong Kong) Limited",
                    "Bank of China (Hong Kong) Limited",
                    "Bank of Communications (Hong Kong) Limited",
                    "CMB WING LUNG BANK",
                    "CMB Wing Lung Bank",
                    "China CITIC Bank International Limited",
                    "China Construction Bank (Asia)",
                    "Chiyu Banking Corporation Limited",
                    "Chong Hing Bank",
                    "Citibank (Hong Kong) Limited",
                    "DBS Bank (Hong Kong) Limited",
                    "Dah Sing Bank",
                    "Fubon Bank (Hong Kong) Limited",
                    "Hang Seng Bank Limited",
                    "Industrial and Commercial Bank of China ( Asia ) Limited",
                    "Industrial and Commercial Bank of China (Asia) Limited",
                    "Nanyang Commercial Bank",
                    "Nanyang Commercial Bank, Limited",
                    "OCBC Bank (Hong Kong) Limited",
                    "Public Bank (Hong Kong) Limited",
                    "Shanghai Commercial Bank Limited",
                    "Standard Chartered Bank (Hong Kong) Limited",
                    "The Bank of East Asia Limited",
                    "The Hongkong and Shanghai Banking Corporation Limited",
                )

            database.bankDao.getDistinctBankNames(language = "zh") shouldBe
                listOf(
                    " 交通銀行(香港)有限公司",
                    "上海商業銀行有限公司",
                    "中信銀行(國際)有限公司",
                    "中國工商銀行(亞洲)有限公司",
                    "中國建設銀行(亞洲)",
                    "中國銀行(香港)有限公司",
                    "交通銀行(香港)有限公司",
                    "創興銀行",
                    "南洋商業銀行",
                    "南洋商業銀行有限公司",
                    "大新銀行",
                    "大眾銀行 (香港) 有限公司",
                    "富邦銀行(香港)有限公司",
                    "恒生銀行有限公司",
                    "招商永隆銀行",
                    "星展銀行(香港)有限公司",
                    "東亞銀行有限公司",
                    "渣打銀行(香港)有限公司",
                    "花旗銀行(香港)有限公司",
                    "華僑銀行 (香港) 有限公司",
                    "集友銀行有限公司",
                    "香港上海滙豐銀行有限公司",
                )
        }
    }

    @Test
    fun testGetDistricts() {
        runTest(testDispatcher) {
            val database = createDatabaseFromAsset()

            database.bankDao.getDistricts(language = "en") shouldBe
                listOf(
                    " Kwun Tong District",
                    "Central & Western",
                    "Central & Western District",
                    "Central and Western",
                    "Central and Western District",
                    "Central and Western District ",
                    "CentralNWestern",
                    "Cheung Chau",
                    "Eastern",
                    "Eastern District",
                    "Islands",
                    "Islands District",
                    "Kowloon City",
                    "Kowloon City District",
                    "KowloonCity",
                    "Kwai Tsing",
                    "Kwai Tsing District",
                    "Kwai Tsing District ",
                    "KwaiTsing",
                    "Kwun Tong",
                    "Kwun Tong District",
                    "KwunTong",
                    "Lamma Island",
                    "Lantau Island",
                    "North",
                    "North District",
                    "North District ",
                    "Northern",
                    "Northern District",
                    "Outlying Island District",
                    "Outlying Islands",
                    "Peng Chau",
                    "Sai Kung",
                    "Sai Kung District",
                    "SaiKung",
                    "Sha Tin",
                    "Sha Tin District",
                    "ShaTin",
                    "Sham Shui Po",
                    "Sham Shui Po District",
                    "Sham Shui Po District ",
                    "ShamShuiPo",
                    "Shatin",
                    "Shatin District",
                    "Shum Shui Po District",
                    "Southern",
                    "Southern District",
                    "Tai Po",
                    "Tai Po District",
                    "TaiPo",
                    "Tsuen Wan",
                    "Tsuen Wan District",
                    "TsuenWan",
                    "Tuen Mun",
                    "Tuen Mun District",
                    "TuenMun",
                    "Wan Chai",
                    "Wan Chai District",
                    "Wan Chai District ",
                    "WanChai",
                    "Wanchai",
                    "Wong Tai Sin",
                    "Wong Tai Sin District",
                    "WongTaiSin",
                    "Yau Tsim Mong",
                    "Yau Tsim Mong District",
                    "Yau Tsui Mong",
                    "Yau Tsui Mong District",
                    "YauTsimMong",
                    "Yuen Long",
                    "Yuen Long District",
                    "YuenLong",
                )

            database.bankDao.getDistricts(language = "zh") shouldBe
                listOf(
                    "中西區",
                    "九龍城",
                    "九龍城區",
                    "元朗",
                    "元朗區",
                    "北區",
                    "南丫島",
                    "南區",
                    "坪洲",
                    "大埔",
                    "大埔區",
                    "大嶼山",
                    "屯門",
                    "屯門區",
                    "東區",
                    "沙田",
                    "沙田區",
                    "油尖旺",
                    "油尖旺區",
                    "深水埗",
                    "深水埗區",
                    "灣仔",
                    "灣仔區",
                    "荃灣",
                    "荃灣區",
                    "葵青",
                    "葵青/kuiqing",
                    "葵青區",
                    "西貢",
                    "西貢區",
                    "觀塘",
                    "觀塘區",
                    "長洲",
                    "離島",
                    "離島區",
                    "黃大仙",
                    "黃大仙區",
                )
        }
    }

    @Test
    fun testGetAll() {
        runTest(testDispatcher) {
            val database = createDatabaseFromAsset()

            database.bankDao.getAll().size shouldBe 6194
        }
    }

    private fun createDatabaseFromAsset(): BankDatabase {
        return Room
            .databaseBuilder(
                context,
                BankDatabase::class.java,
                "bank_db.db",
            ).createFromAsset("bank_db.db")
            .build()
    }
}
