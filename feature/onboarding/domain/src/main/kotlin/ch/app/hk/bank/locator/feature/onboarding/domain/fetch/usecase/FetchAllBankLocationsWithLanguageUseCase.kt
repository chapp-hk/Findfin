package ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase

interface FetchAllBankLocationsWithLanguageUseCase {
    suspend operator fun invoke(): Boolean
}
