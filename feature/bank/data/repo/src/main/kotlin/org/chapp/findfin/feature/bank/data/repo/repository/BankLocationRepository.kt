package org.chapp.findfin.feature.bank.data.repo.repository

import org.chapp.findfin.feature.bank.data.repo.mapper.BankLocationFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationModel
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationType

interface BankLocationRepository {
    suspend fun fetchLocations(
        type: BankLocationType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): BankLocationFetchResult

    suspend fun getLocationsWithinBound(
        language: String,
        bound: BankLocationBound,
    ): List<BankLocationModel>

    suspend fun getAllBanks(): List<String>

    suspend fun getAll(): List<BankLocationModel>
}
