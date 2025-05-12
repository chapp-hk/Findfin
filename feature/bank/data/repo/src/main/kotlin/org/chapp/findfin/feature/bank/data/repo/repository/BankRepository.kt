package org.chapp.findfin.feature.bank.data.repo.repository

import org.chapp.findfin.feature.bank.data.repo.mapper.BankFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankType

interface BankRepository {
    suspend fun fetchBanks(
        type: BankType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): BankFetchResult

    suspend fun getAllBanks(): List<String>

    suspend fun getBanksByParameters(
        name: String? = null,
        type: BankType? = null,
        bound: BankLocationBound? = null,
    ): List<BankModel>
}
