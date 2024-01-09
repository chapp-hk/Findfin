package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.locator.data.local.datasource.LocatorLocalDataSource
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorType
import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource
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
        override suspend fun fetchBanks(
            type: LocatorType,
            language: String,
            pageSize: Int,
        ) {
            val mapper = Mappers.getMapper(LocatorMapper::class.java)
            var page = 0
            do {
                val remoteBanks =
                    locatorRemoteDataSource.getBanks(
                        type = type,
                        language = language,
                        pageSize = pageSize,
                        offset = page * pageSize,
                    )

                locatorLocalDataSource.insertAll(remoteBanks.map { mapper.convertToLocal(type, it) })

                page++
            } while (remoteBanks.size == pageSize)
        }
    }
