package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorType

interface LocatorRepository {
    suspend fun fetchLocators(
        type: LocatorType,
        language: String,
        pageSize: Int,
    )
}
