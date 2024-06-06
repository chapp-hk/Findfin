package ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase

import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.hk.bank.locator.feature.locator.data.repo.mapper.LocatorFetchResult
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorType
import ch.app.hk.bank.locator.feature.locator.data.repo.repository.LocatorRepository
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

@DisplayName("FetchAllLocatorsWithLanguageUseCaseImpl unit tests")
class FetchAllLocatorsWithLanguageUseCaseImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locatorRepository = mockk<LocatorRepository>()
    private val appLocaleRepository = mockk<AppLocaleRepository>()

    private val fetchAllLocatorsWithLanguage =
        FetchAllLocatorsWithLanguageUseCaseImpl(
            defaultDispatcher = testDispatcher,
            locatorRepository = locatorRepository,
            appLocaleRepository = appLocaleRepository,
        )

    @ParameterizedTest(
        name =
            "When branch last result is {0} and atm last result is {1}, " +
                "then invoke should return {2}",
    )
    @ArgumentsSource(FetchArgumentsProvider::class)
    fun testInvokeResult(
        branchMockedResult: LocatorFetchResult,
        atmMockedResult: LocatorFetchResult,
        expectedResult: Boolean,
    ) = runTest(testDispatcher) {
        coEvery {
            locatorRepository.fetchLocators(
                type = any(),
                localeTag = any(),
                page = 0,
                pageSize = any(),
            )
        } returns LocatorFetchResult.HasNext

        coEvery {
            locatorRepository.fetchLocators(
                type = LocatorType.BRANCH,
                localeTag = any(),
                page = 1,
                pageSize = any(),
            )
        } returns branchMockedResult

        coEvery {
            locatorRepository.fetchLocators(
                type = LocatorType.ATM,
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
                    LocatorFetchResult.Error(Throwable()),
                    LocatorFetchResult.Error(Throwable()),
                    false,
                ),
                arguments(
                    LocatorFetchResult.End,
                    LocatorFetchResult.Error(Throwable()),
                    false,
                ),
                arguments(
                    LocatorFetchResult.Error(Throwable()),
                    LocatorFetchResult.End,
                    false,
                ),
                arguments(
                    LocatorFetchResult.End,
                    LocatorFetchResult.End,
                    true,
                ),
            )
    }
}
