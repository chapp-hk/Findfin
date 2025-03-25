package org.chapp.findfin.feature.setting.domain.fetch.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.threading.DispatcherDefault
import org.chapp.findfin.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationType
import org.chapp.findfin.feature.bank.data.repo.location.repository.BankLocationRepository
import org.chapp.findfin.feature.setting.data.repo.language.repository.LanguageRepository
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
class FetchAllBankLocationsWithLanguageUseCaseImpl @Inject constructor(
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
    private val bankLocationRepository: BankLocationRepository,
    private val languageRepository: LanguageRepository,
) : FetchAllBankLocationsWithLanguageUseCase {
    override suspend operator fun invoke(): Boolean {
        return withContext(defaultDispatcher) {
            languageRepository.getAvailableLanguages().filterNot { it.isDefault }.map { language ->
                val isFetchBranchSuccess = getBankLocations(language.tag, BankLocationType.BRANCH)
                val isFetchAtmSuccess = getBankLocations(language.tag, BankLocationType.ATM)
                isFetchBranchSuccess && isFetchAtmSuccess
            }.all { it }
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
            page += 1

            if (result is BankLocationFetchResult.Error) {
                return false
            }
        } while (result is BankLocationFetchResult.HasNext)

        return true
    }
}
