package ch.app.hk.bank.locator.core.database.impl

import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class AppDatabaseInject
    @AssistedInject
    constructor(
        private val appDatabaseBuilder: AppDatabaseBuilder,
        @Assisted private val databaseName: String,
    ) {
        fun provideDatabase() {
        }
    }
