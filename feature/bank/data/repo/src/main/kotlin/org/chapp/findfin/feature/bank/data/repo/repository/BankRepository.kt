package org.chapp.findfin.feature.bank.data.repo.repository

import org.chapp.findfin.feature.bank.data.repo.model.BankFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankType

/**
 * Repository interface for managing bank-related data.
 */
interface BankRepository {
    /**
     * Fetches a paginated list of banks based on the specified type and locale.
     *
     * @param type The type of the bank (e.g., ATM, BRANCH).
     * @param localeTag The locale tag to filter banks by language or region.
     * @param page The page number for pagination.
     * @param pageSize The number of items per page.
     * @return A [BankFetchResult] containing the fetched banks and pagination details.
     */
    suspend fun fetchBanks(
        type: BankType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): BankFetchResult

    /**
     * Retrieves a list of all bank names.
     *
     * @return A list of bank names as [String].
     */
    suspend fun getAllBanks(): List<String>

    /**
     * Retrieves a list of banks based on the specified parameters.
     *
     * @param name The name of the bank to filter by (optional).
     * @param type The type of the bank (e.g., ATM, BRANCH) to filter by (optional).
     * @param bound The geographical bounds to filter banks within (optional).
     * @return A list of [BankModel] objects matching the specified parameters.
     */
    suspend fun getBanksByParameters(
        name: String? = null,
        type: BankType? = null,
        bound: BankLocationBound? = null,
    ): List<BankModel>
}
