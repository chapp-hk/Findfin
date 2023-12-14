package ch.app.hk.bank.locator.feature.locator.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import ch.app.hk.bank.locator.feature.locator.data.local.database.entity.BankEntity

@Dao
interface LocatorRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(banks: List<BankEntity>)
}
