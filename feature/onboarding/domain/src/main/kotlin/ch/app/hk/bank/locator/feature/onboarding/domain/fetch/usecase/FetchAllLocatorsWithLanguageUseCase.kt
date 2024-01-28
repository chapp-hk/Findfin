package ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase

interface FetchAllLocatorsWithLanguageUseCase {
    suspend operator fun invoke(): Boolean
}
