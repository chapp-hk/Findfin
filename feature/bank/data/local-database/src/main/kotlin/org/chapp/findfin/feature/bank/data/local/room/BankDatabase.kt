package org.chapp.findfin.feature.bank.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.chapp.findfin.feature.bank.data.local.database.dao.BankLocationDao
import org.chapp.findfin.feature.bank.data.local.database.model.BankLocationEntity
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
