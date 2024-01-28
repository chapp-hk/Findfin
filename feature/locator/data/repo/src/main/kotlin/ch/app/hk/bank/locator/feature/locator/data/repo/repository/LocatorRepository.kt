package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.hk.bank.locator.feature.locator.data.repo.model.Locator
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorResult

interface LocatorRepository {
    suspend fun fetchLocators(
        type: Locator,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): LocatorResult
}
