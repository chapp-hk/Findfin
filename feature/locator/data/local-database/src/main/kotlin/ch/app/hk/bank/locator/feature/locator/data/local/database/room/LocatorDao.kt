package ch.app.hk.bank.locator.feature.locator.data.local.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ch.app.hk.bank.locator.feature.locator.data.local.database.model.LocatorEntity

@Dao
interface LocatorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locators: List<LocatorEntity>)
}
