package org.chapp.findfin.feature.setting.domain.fetch.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.core.threading.DispatcherDefault
import org.chapp.findfin.feature.bank.data.repo.model.BankFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
class FetchAllBanksWithLanguageUseCaseImpl @Inject constructor(
    @param:DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
    private val bankRepository: BankRepository,
    private val localeProviderManager: LocaleProviderManager,
) : FetchAllBanksWithLanguageUseCase {
    override suspend operator fun invoke(): Boolean {
        return withContext(defaultDispatcher) {
            localeProviderManager
                .getAvailableLanguages()
                .filterNot { it.isDefault }
                .map { language ->
                    val isFetchBranchSuccess =
                        getBankLocations(language.localeTag, BankType.BRANCH)
                    val isFetchAtmSuccess =
                        getBankLocations(language.localeTag, BankType.ATM)
                    isFetchBranchSuccess && isFetchAtmSuccess
                }.all { it }
        }
    }

    private suspend fun getBankLocations(
        languageTag: String,
        type: BankType,
    ): Boolean {
        var page = 0

        do {
            val result =
                bankRepository.fetchBanks(
                    type = type,
                    localeTag = languageTag,
                    page = page,
                    pageSize = 1000,
                )
            page += 1

            if (result is BankFetchResult.Error) {
                return false
            }
        } while (result is BankFetchResult.HasNext)

        return true
    }
}
