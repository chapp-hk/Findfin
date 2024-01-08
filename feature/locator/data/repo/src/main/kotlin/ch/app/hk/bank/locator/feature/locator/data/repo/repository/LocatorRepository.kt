package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource

interface LocatorRepository {
    suspend fun fetchBanks(
        type: LocatorRemoteDataSource.Type,
        language: String,
        pageSize: Int,
    )
}
