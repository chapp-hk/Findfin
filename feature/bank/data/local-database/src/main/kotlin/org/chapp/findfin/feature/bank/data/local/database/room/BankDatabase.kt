package org.chapp.findfin.feature.bank.data.local.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.chapp.findfin.feature.bank.data.local.database.location.dao.BankLocationDao
import org.chapp.findfin.feature.bank.data.local.database.location.model.BankLocationEntity
import org.chapp.library.hiltwrap.annotation.HiltWrapRoomDao
import org.chapp.library.hiltwrap.annotation.HiltWrapRoomModule

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
