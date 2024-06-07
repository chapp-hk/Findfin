package ch.app.hk.bank.locator.feature.locator.data.local.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.app.hk.bank.locator.feature.locator.data.local.database.model.LocatorEntity

@Dao
interface LocatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locators: List<LocatorEntity>)

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
    ): List<LocatorEntity>
}
