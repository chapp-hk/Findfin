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

    @Deprecated("Use getBanksByParameters() instead")
    suspend fun getBanksWithinBound(bound: BankLocationBound): List<BankModel>

    suspend fun getAllBanks(): List<String>

    @Deprecated("Use getBanksByParameters() instead")
    suspend fun getAll(): List<BankModel>

    suspend fun getBanksByParameters(
        name: String? = null,
        type: BankType? = null,
        bound: BankLocationBound? = null,
    ): List<BankModel>
}
