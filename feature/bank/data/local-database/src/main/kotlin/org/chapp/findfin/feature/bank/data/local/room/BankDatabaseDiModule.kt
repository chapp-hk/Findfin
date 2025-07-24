package org.chapp.findfin.feature.bank.data.local.room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class BankDatabaseDiModule {
    @Provides
    fun provideBankDatabase(bankDatabaseProvider: BankDatabaseProvider) = bankDatabaseProvider.createDatabase()

    @Provides
    fun provideBankDao(bankDatabase: BankDatabase) = bankDatabase.bankDao
}
