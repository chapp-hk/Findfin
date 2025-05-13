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

            database.query("SELECT * FROM bank", null).use {
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

            database.query("SELECT * FROM bank", null).use {
                it.count shouldBe 2
            }
        }
    }

    @Test
    fun testGetDistinctBankNames() {
        runTest(testDispatcher) {
            val database = createDatabaseFromAsset()

            database.bankDao.getDistinctBankNames(language = "en") shouldBe
                listOf(
                    "Bank of China (Hong Kong)",
                    "Bank of Communications (Hong Kong)",
                    "CMB Wing Lung Bank",
                    "China CITIC Bank International",
                    "China Construction Bank (Asia)",
                    "Chiyu Banking Corporation",
                    "Chong Hing Bank",
                    "Citibank (Hong Kong)",
                    "DBS Bank (Hong Kong)",
                    "Dah Sing Bank",
                    "Fubon Bank (Hong Kong)",
                    "Hang Seng Bank",
                    "Industrial and Commercial Bank of China (Asia)",
                    "Nanyang Commercial Bank",
                    "OCBC Bank (Hong Kong)",
                    "Public Bank (Hong Kong)",
                    "Shanghai Commercial Bank",
                    "Standard Chartered Bank (Hong Kong)",
                    "The Bank of East Asia",
                    "The Hongkong and Shanghai Banking Corporation",
                )

            database.bankDao.getDistinctBankNames(language = "zh") shouldBe
                listOf(
                    "上海商業銀行",
                    "中信銀行(國際)",
                    "中國工商銀行(亞洲)",
                    "中國建設銀行(亞洲)",
                    "中國銀行(香港)",
                    "交通銀行(香港)",
                    "創興銀行",
                    "南洋商業銀行",
                    "大新銀行",
                    "大眾銀行 (香港)",
                    "富邦銀行(香港)",
                    "恒生銀行",
                    "招商永隆銀行",
                    "星展銀行(香港)",
                    "東亞銀行",
                    "渣打銀行(香港)",
                    "花旗銀行(香港)",
                    "華僑銀行 (香港)",
                    "集友銀行",
                    "香港上海滙豐銀行",
                )
        }
    }

    @Test
    fun testGetDistricts() {
        runTest(testDispatcher) {
            val database = createDatabaseFromAsset()

            database.bankDao.getDistricts(language = "en") shouldBe
                listOf(
                    "Central & Western",
                    "Eastern",
                    "Islands",
                    "Kowloon City",
                    "Kwai Tsing",
                    "Kwun Tong",
                    "North",
                    "Sai Kung",
                    "Sha Tin",
                    "Sham Shui Po",
                    "Southern",
                    "Tai Po",
                    "Tsuen Wan",
                    "Tuen Mun",
                    "Wan Chai",
                    "Wong Tai Sin",
                    "Yau Tsim Mong",
                    "Yuen Long",
                )

            database.bankDao.getDistricts(language = "zh") shouldBe
                listOf(
                    "中西區",
                    "九龍城區",
                    "元朗區",
                    "北區",
                    "南區",
                    "大埔區",
                    "屯門區",
                    "東區",
                    "沙田區",
                    "油尖旺區",
                    "深水埗區",
                    "灣仔區",
                    "荃灣區",
                    "葵青區",
                    "西貢區",
                    "觀塘區",
                    "離島區",
                    "黃大仙區",
                )
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
