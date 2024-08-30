package ch.app.hk.bank.locator.feature.bank.data.local.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.app.hk.bank.locator.feature.bank.data.local.database.model.LocatorEntity
import ch.app.library.hiltwrap.annotation.HiltWrapRoomDao
import ch.app.library.hiltwrap.annotation.HiltWrapRoomModule

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        LocatorEntity::class,
    ],
)
@HiltWrapRoomModule(
    databaseName = "locator.db",
)
internal abstract class LocatorDatabase : RoomDatabase() {
    @HiltWrapRoomDao
    abstract val locatorDao: LocatorDao
}
