package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.hk.bank.locator.feature.locator.data.repo.model.Locator

interface LocatorRepository {
    suspend fun fetchLocators(
        type: Locator,
        language: String,
        pageSize: Int,
    )
}
