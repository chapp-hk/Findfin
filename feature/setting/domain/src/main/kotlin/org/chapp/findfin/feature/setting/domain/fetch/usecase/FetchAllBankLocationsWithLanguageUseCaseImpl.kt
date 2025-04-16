package org.chapp.findfin.feature.setting.domain.fetch.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.core.threading.DispatcherDefault
import org.chapp.findfin.feature.bank.data.repo.mapper.BankLocationFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationType
import org.chapp.findfin.feature.bank.data.repo.repository.BankLocationRepository
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
class FetchAllBankLocationsWithLanguageUseCaseImpl @Inject constructor(
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
    private val bankLocationRepository: BankLocationRepository,
    private val localeProviderManager: LocaleProviderManager,
) : FetchAllBankLocationsWithLanguageUseCase {
    override suspend operator fun invoke(): Boolean {
        return withContext(defaultDispatcher) {
            localeProviderManager
                .getAvailableLanguages()
                .filterNot { it.isDefault }
                .map { language ->
                    val isFetchBranchSuccess =
                        getBankLocations(language.localeTag, BankLocationType.BRANCH)
                    val isFetchAtmSuccess =
                        getBankLocations(language.localeTag, BankLocationType.ATM)
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
