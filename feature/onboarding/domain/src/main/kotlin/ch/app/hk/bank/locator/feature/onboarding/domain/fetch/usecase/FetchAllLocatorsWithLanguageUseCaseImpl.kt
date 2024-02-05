package ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.hk.bank.locator.core.threading.DispatcherDefault
import ch.app.hk.bank.locator.feature.locator.data.repo.model.Locator
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorResult
import ch.app.hk.bank.locator.feature.locator.data.repo.repository.LocatorRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
class FetchAllLocatorsWithLanguageUseCaseImpl
    @Inject
    constructor(
        @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
        private val locatorRepository: LocatorRepository,
        private val appLocaleRepository: AppLocaleRepository,
    ) : FetchAllLocatorsWithLanguageUseCase {
        override suspend operator fun invoke(): Boolean {
            return withContext(defaultDispatcher) {
                val isFetchBranchSuccess = getLocators(Locator.BRANCH)
                val isFetchAtmSuccess = getLocators(Locator.ATM)
                isFetchBranchSuccess && isFetchAtmSuccess
            }
        }

        private suspend fun getLocators(type: Locator): Boolean {
            var page = 0

            do {
                val result =
                    locatorRepository.fetchLocators(
                        type = type,
                        localeTag = appLocaleRepository.getCurrentLocale()?.toLanguageTag().orEmpty(),
                        page = page,
                        pageSize = 1000,
                    )
                page++

                if (result is LocatorResult.Error) {
                    return false
                }
            } while (result is LocatorResult.HasNext)

            return true
        }
    }
