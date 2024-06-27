package ch.app.hk.bank.locator.feature.home.domain.nearby.usecase

import ch.app.hk.bank.locator.feature.home.domain.nearby.model.NearByResult

interface GetNearByServicesUseCase {
    suspend operator fun invoke(): NearByResult
}
