package org.chapp.findfin.feature.bank.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity

@Dao
internal interface BankDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locators: List<BankEntity>)

    @Query(
        """
        SELECT * FROM bank
        WHERE
            language = :language
            AND latitude BETWEEN :minLat AND :maxLat
            AND longitude BETWEEN :minLon AND :maxLon
        """,
    )
    suspend fun getBanksWithinBound(
        language: String,
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<BankEntity>

    @Query("SELECT DISTINCT bank_name FROM bank WHERE language = :language ORDER BY bank_name")
    suspend fun getDistinctBankNames(language: String): List<String>

    @Query("SELECT DISTINCT district FROM bank WHERE language = :language ORDER BY district")
    suspend fun getDistricts(language: String): List<String>

    @Query("SELECT * FROM bank")
    suspend fun getAll(): List<BankEntity>
}
