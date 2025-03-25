package org.chapp.findfin.feature.home.domain.nearby.usecase

import org.chapp.findfin.feature.home.domain.nearby.model.NearByResult

interface GetNearByServicesUseCase {
    suspend operator fun invoke(language: String): NearByResult
}
