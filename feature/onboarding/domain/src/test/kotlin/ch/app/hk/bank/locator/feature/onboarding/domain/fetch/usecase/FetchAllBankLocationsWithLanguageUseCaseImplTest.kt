package ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase

import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType
import ch.app.hk.bank.locator.feature.bank.data.repo.location.repository.BankLocationRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.Locale
import java.util.stream.Stream

@DisplayName("FetchAllBankLocationsWithLanguageUseCaseImpl unit tests")
class FetchAllBankLocationsWithLanguageUseCaseImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val bankLocationRepository = mockk<BankLocationRepository>()
    private val appLocaleRepository = mockk<AppLocaleRepository>()

    private val fetchAllLocatorsWithLanguage =
        FetchAllBankLocationsWithLanguageUseCaseImpl(
            defaultDispatcher = testDispatcher,
            bankLocationRepository = bankLocationRepository,
            appLocaleRepository = appLocaleRepository,
        )

    @ParameterizedTest(
        name =
            "When branch last result is {0} and atm last result is {1}, " +
                "then invoke should return {2}",
    )
    @ArgumentsSource(FetchArgumentsProvider::class)
    fun testInvokeResult(
        branchMockedResult: BankLocationFetchResult,
        atmMockedResult: BankLocationFetchResult,
        expectedResult: Boolean,
    ) = runTest(testDispatcher) {
        coEvery {
            bankLocationRepository.fetchLocations(
                type = any(),
                localeTag = any(),
                page = 0,
                pageSize = any(),
            )
        } returns BankLocationFetchResult.HasNext

        coEvery {
            bankLocationRepository.fetchLocations(
                type = BankLocationType.BRANCH,
                localeTag = any(),
                page = 1,
                pageSize = any(),
            )
        } returns branchMockedResult

        coEvery {
            bankLocationRepository.fetchLocations(
                type = BankLocationType.ATM,
                localeTag = any(),
                page = 1,
                pageSize = any(),
            )
        } returns atmMockedResult

        every { appLocaleRepository.getCurrentLocale() } returns Locale("en")

        fetchAllLocatorsWithLanguage() shouldBe expectedResult
    }

    private class FetchArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext): Stream<Arguments> =
            Stream.of(
                arguments(
                    BankLocationFetchResult.Error,
                    BankLocationFetchResult.Error,
                    false,
                ),
                arguments(
                    BankLocationFetchResult.End,
                    BankLocationFetchResult.Error,
                    false,
                ),
                arguments(
                    BankLocationFetchResult.Error,
                    BankLocationFetchResult.End,
                    false,
                ),
                arguments(
                    BankLocationFetchResult.End,
                    BankLocationFetchResult.End,
                    true,
                ),
            )
    }
}
