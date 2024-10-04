package org.chapp.findfin.feature.bank.data.repo.location.repository

import org.chapp.findfin.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationModel
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationType

interface BankLocationRepository {
    suspend fun fetchLocations(
        type: BankLocationType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): BankLocationFetchResult

    suspend fun getLocationsWithinBound(bound: BankLocationBound): List<BankLocationModel>

    suspend fun getAllBanks(): List<String>
}
