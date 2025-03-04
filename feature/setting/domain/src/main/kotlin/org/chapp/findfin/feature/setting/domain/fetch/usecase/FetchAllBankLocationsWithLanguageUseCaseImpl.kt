package org.chapp.findfin.feature.setting.domain.fetch.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.threading.DispatcherDefault
import org.chapp.findfin.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationType
import org.chapp.findfin.feature.bank.data.repo.location.repository.BankLocationRepository
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
class FetchAllBankLocationsWithLanguageUseCaseImpl @Inject constructor(
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
    private val bankLocationRepository: BankLocationRepository,
) : FetchAllBankLocationsWithLanguageUseCase {
    override suspend operator fun invoke(languageTag: String): Boolean {
        return withContext(defaultDispatcher) {
            val isFetchBranchSuccess = getBankLocations(languageTag, BankLocationType.BRANCH)
            val isFetchAtmSuccess = getBankLocations(languageTag, BankLocationType.ATM)
            isFetchBranchSuccess && isFetchAtmSuccess
        }
    }

    private suspend fun getBankLocations(
        languageTag: String,
        type: BankLocationType,
    ): Boolean {
        var page = 0

        do {
            val result =
                bankLocationRepository.fetchLocations(
                    type = type,
                    localeTag = languageTag,
                    page = page,
                    pageSize = 1000,
                )
            page++

            if (result is BankLocationFetchResult.Error) {
                return false
            }
        } while (result is BankLocationFetchResult.HasNext)

        return true
    }
}
