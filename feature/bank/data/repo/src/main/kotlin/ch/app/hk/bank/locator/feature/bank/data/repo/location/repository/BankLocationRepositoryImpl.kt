package ch.app.hk.bank.locator.feature.bank.data.repo.location.repository

import ch.app.hk.bank.locator.feature.bank.data.local.bank.datasource.BankLocationLocalDataSource
import ch.app.hk.bank.locator.feature.bank.data.remote.location.datasource.BankLocationRemoteDataSource
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.LocatorResult
import ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper.BankLocationMapper
import ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper.toApiLang
import ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper.toRemoteLocatorPath
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationBound
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationModel
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltWrapBindModule
internal class BankLocationRepositoryImpl @Inject constructor(
    private val bankLocationLocalDataSource: BankLocationLocalDataSource,
    private val bankLocationRemoteDataSource: BankLocationRemoteDataSource,
) : BankLocationRepository {
    private val mapper = Mappers.getMapper(BankLocationMapper::class.java)

    override suspend fun fetchLocators(
        type: BankLocationType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): BankLocationFetchResult {
        val locatorPath = type.toRemoteLocatorPath()

        val remoteResult =
            bankLocationRemoteDataSource.getLocators(
                path = locatorPath,
                language = localeTag.toApiLang(),
                pageSize = pageSize,
                offset = page * pageSize,
            )

        return when (remoteResult) {
            LocatorResult.Error -> {
                BankLocationFetchResult.Error
            }

            is LocatorResult.Success -> {
                remoteResult
                    .data
                    .map { mapper.convertToLocal(locatorPath, it) }
                    .also { bankLocationLocalDataSource.insertAll(it) }
                    .let { list ->
                        if (list.size < pageSize) {
                            BankLocationFetchResult.End
                        } else {
                            BankLocationFetchResult.HasNext
                        }
                    }
            }
        }
    }

    override suspend fun getLocatorsWithinBound(bound: BankLocationBound): List<BankLocationModel> {
        return bankLocationLocalDataSource.getBanksWithinBound(
            minLat = bound.minLat,
            maxLat = bound.maxLat,
            minLon = bound.minLong,
            maxLon = bound.maxLong,
        ).map(mapper::convertToDataModel)
    }

    override suspend fun getAllBanks(): List<String> {
        return bankLocationLocalDataSource.getAllBanks()
    }
}
