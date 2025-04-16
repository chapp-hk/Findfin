package org.chapp.findfin.feature.bank.data.repo.repository

import org.chapp.findfin.feature.bank.data.remote.network.datasource.BankLocationRemoteDataSource
import org.chapp.findfin.feature.bank.data.remote.network.model.LocationResult
import org.chapp.findfin.feature.bank.data.repo.local.datasource.BankLocationLocalDataSource
import org.chapp.findfin.feature.bank.data.repo.mapper.BankLocationFetchResult
import org.chapp.findfin.feature.bank.data.repo.mapper.BankLocationMapper
import org.chapp.findfin.feature.bank.data.repo.mapper.toApiLang
import org.chapp.findfin.feature.bank.data.repo.mapper.toLocalLanguage
import org.chapp.findfin.feature.bank.data.repo.mapper.toRemoteLocationPath
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationModel
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationType
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltWrapBindModule
internal class BankLocationRepositoryImpl @Inject constructor(
    private val bankLocationLocalDataSource: BankLocationLocalDataSource,
    private val bankLocationRemoteDataSource: BankLocationRemoteDataSource,
) : BankLocationRepository {
    private val mapper = Mappers.getMapper(BankLocationMapper::class.java)

    override suspend fun fetchLocations(
        type: BankLocationType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): BankLocationFetchResult {
        val locatorPath = type.toRemoteLocationPath()

        val remoteResult =
            bankLocationRemoteDataSource.getLocations(
                path = locatorPath,
                language = localeTag.toApiLang(),
                pageSize = pageSize,
                offset = page * pageSize,
            )

        return when (remoteResult) {
            LocationResult.Error -> {
                BankLocationFetchResult.Error
            }

            is LocationResult.Success -> {
                remoteResult
                    .data
                    .map {
                        mapper.convertToLocal(
                            language = localeTag,
                            type = locatorPath,
                            locator = it,
                        )
                    }
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

    override suspend fun getLocationsWithinBound(
        language: String,
        bound: BankLocationBound,
    ): List<BankLocationModel> {
        return bankLocationLocalDataSource.getBanksWithinBound(
            language = language.toLocalLanguage(),
            minLat = bound.minLat,
            maxLat = bound.maxLat,
            minLon = bound.minLong,
            maxLon = bound.maxLong,
        ).map(mapper::convertToDataModel)
    }

    override suspend fun getAllBanks(): List<String> {
        return bankLocationLocalDataSource.getAllBanks()
    }

    override suspend fun getAll(): List<BankLocationModel> {
        return bankLocationLocalDataSource
            .getAll()
            .map(mapper::convertToDataModel)
    }
}
