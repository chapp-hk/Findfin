package ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase

import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.hk.bank.locator.core.threading.DispatcherDefault
import ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType
import ch.app.hk.bank.locator.feature.bank.data.repo.location.repository.BankLocationRepository
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltWrapBindModule
class FetchAllBankLocationsWithLanguageUseCaseImpl @Inject constructor(
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
    private val bankLocationRepository: BankLocationRepository,
    private val appLocaleRepository: AppLocaleRepository,
) : FetchAllBankLocationsWithLanguageUseCase {
    override suspend operator fun invoke(): Boolean {
        return withContext(defaultDispatcher) {
            val isFetchBranchSuccess = getBankLocations(BankLocationType.BRANCH)
            val isFetchAtmSuccess = getBankLocations(BankLocationType.ATM)
            isFetchBranchSuccess && isFetchAtmSuccess
        }
    }

    private suspend fun getBankLocations(type: BankLocationType): Boolean {
        var page = 0

        do {
            val result =
                bankLocationRepository.fetchLocations(
                    type = type,
                    localeTag = appLocaleRepository.getCurrentLocale().toLanguageTag(),
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
