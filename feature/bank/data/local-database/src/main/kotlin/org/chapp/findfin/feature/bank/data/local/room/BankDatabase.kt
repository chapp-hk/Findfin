package org.chapp.findfin.feature.bank.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.chapp.findfin.feature.bank.data.local.database.dao.BankDao
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity
import org.chapp.library.hiltwrap.annotation.HiltWrapRoomDao
import org.chapp.library.hiltwrap.annotation.HiltWrapRoomModule

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        BankEntity::class,
    ],
)
@HiltWrapRoomModule(
    databaseName = "bank.db",
)
internal abstract class BankDatabase : RoomDatabase() {
    @HiltWrapRoomDao
    abstract val bankDao: BankDao
}
