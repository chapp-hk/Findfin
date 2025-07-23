package org.chapp.findfin.feature.bank.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.chapp.findfin.feature.bank.data.local.database.dao.BankDao
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity

@Database(
    version = 1,
    exportSchema = true,
    entities = [
        BankEntity::class,
    ],
)
internal abstract class BankDatabase : RoomDatabase() {
    abstract val bankDao: BankDao
}
