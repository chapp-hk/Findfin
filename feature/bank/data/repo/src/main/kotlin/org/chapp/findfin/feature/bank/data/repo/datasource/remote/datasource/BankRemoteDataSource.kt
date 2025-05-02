package org.chapp.findfin.feature.bank.data.repo.datasource.remote.datasource

import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemoteResult
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.TypePath

interface BankRemoteDataSource {
    suspend fun getLocations(
        path: TypePath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): BankRemoteResult
}
