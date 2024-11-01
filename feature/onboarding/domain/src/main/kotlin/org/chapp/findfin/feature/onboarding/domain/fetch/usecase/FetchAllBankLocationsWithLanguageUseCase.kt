package org.chapp.findfin.feature.onboarding.domain.fetch.usecase

interface FetchAllBankLocationsWithLanguageUseCase {
    suspend operator fun invoke(languageTag: String): Boolean
}
