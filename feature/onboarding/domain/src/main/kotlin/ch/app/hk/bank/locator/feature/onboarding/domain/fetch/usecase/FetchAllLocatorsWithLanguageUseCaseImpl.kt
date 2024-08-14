package ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase

import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.hk.bank.locator.core.threading.DispatcherDefault
import ch.app.hk.bank.locator.feature.locator.data.repo.mapper.LocatorFetchResult
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorType
import ch.app.hk.bank.locator.feature.locator.data.repo.repository.LocatorRepository
import ch.app.library.hiltext.annotation.HiltExtBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
class FetchAllLocatorsWithLanguageUseCaseImpl @Inject constructor(
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
    private val locatorRepository: LocatorRepository,
    private val appLocaleRepository: AppLocaleRepository,
) : FetchAllLocatorsWithLanguageUseCase {
    override suspend operator fun invoke(): Boolean {
        return withContext(defaultDispatcher) {
            val isFetchBranchSuccess = getLocators(LocatorType.BRANCH)
            val isFetchAtmSuccess = getLocators(LocatorType.ATM)
            isFetchBranchSuccess && isFetchAtmSuccess
        }
    }

    private suspend fun getLocators(type: LocatorType): Boolean {
        var page = 0

        do {
            val result =
                locatorRepository.fetchLocators(
                    type = type,
                    localeTag = appLocaleRepository.getCurrentLocale().toLanguageTag(),
                    page = page,
                    pageSize = 1000,
                )
            page++

            if (result is LocatorFetchResult.Error) {
                return false
            }
        } while (result is LocatorFetchResult.HasNext)

        return true
    }
}
