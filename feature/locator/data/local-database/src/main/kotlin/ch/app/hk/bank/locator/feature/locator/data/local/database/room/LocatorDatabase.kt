package ch.app.hk.bank.locator.feature.locator.data.local.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.app.framework.hiltext.annotation.HiltExtRoomDao
import ch.app.framework.hiltext.annotation.HiltExtRoomModule
import ch.app.hk.bank.locator.feature.locator.data.local.database.dao.LocatorRoomDao
import ch.app.hk.bank.locator.feature.locator.data.local.database.entity.BankEntity

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        BankEntity::class,
    ],
)
@HiltExtRoomModule(
    databaseName = "locator.db",
)
internal abstract class LocatorDatabase : RoomDatabase() {
    @HiltExtRoomDao
    abstract val locatorRoomDao: LocatorRoomDao
}
