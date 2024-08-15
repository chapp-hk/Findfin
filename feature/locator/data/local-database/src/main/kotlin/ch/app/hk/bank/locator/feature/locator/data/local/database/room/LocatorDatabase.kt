package ch.app.hk.bank.locator.feature.locator.data.local.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.app.hk.bank.locator.feature.locator.data.local.database.model.LocatorEntity
import ch.app.library.hiltwrap.annotation.HiltExtRoomDao
import ch.app.library.hiltwrap.annotation.HiltExtRoomModule

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        LocatorEntity::class,
    ],
)
@HiltExtRoomModule(
    databaseName = "locator.db",
)
internal abstract class LocatorDatabase : RoomDatabase() {
    @HiltExtRoomDao
    abstract val locatorDao: LocatorDao
}
