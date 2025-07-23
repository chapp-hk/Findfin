package org.chapp.findfin.feature.bank.data.local.room

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class BankDatabaseProvider @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    fun createDatabase(): BankDatabase {
        return Room
            .databaseBuilder(
                context = context,
                klass = BankDatabase::class.java,
                name = "bank.db",
            )
            .build()
    }
}
