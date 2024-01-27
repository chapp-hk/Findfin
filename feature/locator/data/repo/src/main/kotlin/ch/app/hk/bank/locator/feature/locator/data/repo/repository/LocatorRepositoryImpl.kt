package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.locator.data.local.datasource.LocatorLocalDataSource
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource
import ch.app.hk.bank.locator.feature.locator.data.repo.model.Locator
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorMapper
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltExtBindModule
class LocatorRepositoryImpl
    @Inject
    constructor(
        private val locatorLocalDataSource: LocatorLocalDataSource,
        private val locatorRemoteDataSource: LocatorRemoteDataSource,
    ) : LocatorRepository {
        override suspend fun fetchLocators(
            type: Locator,
            language: String,
            pageSize: Int,
        ) {
            val mapper = Mappers.getMapper(LocatorMapper::class.java)
            val locatorPath = convertToRemoteLocator(type)
            var page = 0
            do {
                val remoteBanks =
                    locatorRemoteDataSource.getLocators(
                        path = locatorPath,
                        language = language,
                        pageSize = pageSize,
                        offset = page * pageSize,
                    )

                locatorLocalDataSource.insertAll(
                    remoteBanks.map { mapper.convertToLocal(locatorPath, it) },
                )

                page++
            } while (remoteBanks.size == pageSize)
        }

        private fun convertToRemoteLocator(locator: Locator): LocatorPath {
            return when (locator) {
                Locator.ATM -> LocatorPath.ATM
                Locator.BRANCH -> LocatorPath.BRANCH
            }
        }
    }
