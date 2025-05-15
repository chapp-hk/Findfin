package org.chapp.findfin.feature.setting.domain.fetch.usecase

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.locale.api.Language
import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.feature.bank.data.repo.model.BankFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

@DisplayName("FetchAllBanksWithLanguageUseCaseImpl unit tests")
class FetchAllBanksWithLanguageUseCaseImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val bankRepository = mockk<BankRepository>()
    private val localeProviderManager = mockk<LocaleProviderManager>()

    private val fetchAllLocatorsWithLanguage =
        FetchAllBanksWithLanguageUseCaseImpl(
            defaultDispatcher = testDispatcher,
            bankRepository = bankRepository,
            localeProviderManager = localeProviderManager,
        )

    @ParameterizedTest(
        name =
            "When branch last result is {0} and atm last result is {1}, " +
                "then invoke should return {2}",
    )
    @ArgumentsSource(FetchArgumentsProvider::class)
    fun testInvokeResult(
        branchMockedResult: BankFetchResult,
        atmMockedResult: BankFetchResult,
        expectedResult: Boolean,
    ) = runTest(testDispatcher) {
        coEvery {
            localeProviderManager.getAvailableLanguages()
        } returns
            listOf(
                Language(isDefault = true, localeTag = "", displayName = "System Default"),
                Language(isDefault = false, localeTag = "en", displayName = "English"),
                Language(isDefault = false, localeTag = "zh", displayName = "Chinese"),
            )

        coEvery {
            bankRepository.fetchBanks(
                type = any(),
                localeTag = any(),
                page = 0,
                pageSize = any(),
            )
        } returns BankFetchResult.HasNext

        coEvery {
            bankRepository.fetchBanks(
                type = BankType.BRANCH,
                localeTag = any(),
                page = 1,
                pageSize = any(),
            )
        } returns branchMockedResult

        coEvery {
            bankRepository.fetchBanks(
                type = BankType.ATM,
                localeTag = any(),
                page = 1,
                pageSize = any(),
            )
        } returns atmMockedResult

        fetchAllLocatorsWithLanguage() shouldBe expectedResult
    }

    private class FetchArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext): Stream<Arguments> =
            Stream.of(
                arguments(
                    BankFetchResult.Error,
                    BankFetchResult.Error,
                    false,
                ),
                arguments(
                    BankFetchResult.End,
                    BankFetchResult.Error,
                    false,
                ),
                arguments(
                    BankFetchResult.Error,
                    BankFetchResult.End,
                    false,
                ),
                arguments(
                    BankFetchResult.End,
                    BankFetchResult.End,
                    true,
                ),
            )
    }
}
