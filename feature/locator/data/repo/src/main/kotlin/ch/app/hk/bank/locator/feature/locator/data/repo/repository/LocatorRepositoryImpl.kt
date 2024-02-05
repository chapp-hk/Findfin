package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.feature.locator.data.local.datasource.LocatorLocalDataSource
import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource
import ch.app.hk.bank.locator.feature.locator.data.repo.model.Locator
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorMapper
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorResult
import ch.app.hk.bank.locator.feature.locator.data.repo.model.toApiLang
import ch.app.hk.bank.locator.feature.locator.data.repo.model.toRemoteLocatorPath
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
            localeTag: String,
            page: Int,
            pageSize: Int,
        ): LocatorResult {
            val mapper = Mappers.getMapper(LocatorMapper::class.java)
            val locatorPath = type.toRemoteLocatorPath()

            val remoteBanks =
                locatorRemoteDataSource.getLocators(
                    path = locatorPath,
                    language = localeTag.toApiLang(),
                    pageSize = pageSize,
                    offset = page * pageSize,
                )

            return runCatching {
                remoteBanks
                    .getOrThrow()
                    .map { mapper.convertToLocal(locatorPath, it) }
                    .also { locatorLocalDataSource.insertAll(it) }
                    .let { list ->
                        if (list.size < pageSize) {
                            LocatorResult.End
                        } else {
                            LocatorResult.HasNext
                        }
                    }
            }.getOrElse { error ->
                LocatorResult.Error(error)
            }
        }
    }
