package ch.app.hk.bank.locator.feature.bank.data.local.database.location.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.app.hk.bank.locator.feature.bank.data.local.database.location.model.BankLocationEntity

@Dao
internal interface BankLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locators: List<BankLocationEntity>)

    @Query(
        """
        SELECT * FROM locator
        WHERE latitude BETWEEN :minLat AND :maxLat
        AND longitude BETWEEN :minLon AND :maxLon
    """,
    )
    suspend fun getLocatorsWithinBound(
        minLat: Double,
        maxLat: Double,
        minLon: Double,
        maxLon: Double,
    ): List<BankLocationEntity>

    @Query("SELECT DISTINCT bank_name FROM locator")
    suspend fun getDistinctBanks(): List<String>
}
