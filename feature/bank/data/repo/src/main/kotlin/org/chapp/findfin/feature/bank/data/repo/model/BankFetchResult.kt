package org.chapp.findfin.feature.bank.data.repo.model

/**
 * Represents the result of a bank fetch operation.
 *
 * This sealed interface defines possible outcomes when fetching banks:
 * - [HasNext]: Indicates that there are more banks to fetch (pagination continues).
 * - [End]: Indicates that there are no more banks to fetch (end of pagination).
 * - [Error]: Indicates that an error occurred during the fetch operation.
 */
sealed interface BankFetchResult {
    data object HasNext : BankFetchResult

    data object End : BankFetchResult

    data object Error : BankFetchResult
}
