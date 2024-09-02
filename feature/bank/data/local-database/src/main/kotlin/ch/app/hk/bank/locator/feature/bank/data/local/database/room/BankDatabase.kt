package ch.app.hk.bank.locator.feature.bank.data.local.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.app.hk.bank.locator.feature.bank.data.local.database.location.dao.BankLocationDao
import ch.app.hk.bank.locator.feature.bank.data.local.database.location.model.BankLocationEntity
import ch.app.library.hiltwrap.annotation.HiltWrapRoomDao
import ch.app.library.hiltwrap.annotation.HiltWrapRoomModule

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        BankLocationEntity::class,
    ],
)
@HiltWrapRoomModule(
    databaseName = "locator.db",
)
internal abstract class BankDatabase : RoomDatabase() {
    @HiltWrapRoomDao
    abstract val bankLocationDao: BankLocationDao
}
