package ch.app.hk.bank.locator.core.database.impl

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppDatabaseBuilder
    @Inject
    constructor(
        @ApplicationContext val context: Context,
    ) {
        inline fun <reified T : RoomDatabase> build(databaseName: String): T {
            return Room.databaseBuilder(
                context = context,
                klass = T::class.java,
                name = databaseName,
            ).build()
        }
    }
