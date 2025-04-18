package org.chapp.findfin.feature.bank.data.repo.repository

import org.chapp.findfin.feature.bank.data.repo.datasource.local.datasource.BankLocalDataSource
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.datasource.BankRemoteDataSource
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemoteResult
import org.chapp.findfin.feature.bank.data.repo.mapper.BankDataMapper
import org.chapp.findfin.feature.bank.data.repo.mapper.BankFetchResult
import org.chapp.findfin.feature.bank.data.repo.mapper.toApiLang
import org.chapp.findfin.feature.bank.data.repo.mapper.toLocalLanguage
import org.chapp.findfin.feature.bank.data.repo.mapper.toRemoteLocationPath
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltWrapBindModule
internal class BankRepositoryImpl @Inject constructor(
    private val bankLocalDataSource: BankLocalDataSource,
    private val bankRemoteDataSource: BankRemoteDataSource,
) : BankRepository {
    private val mapper = Mappers.getMapper(BankDataMapper::class.java)

    override suspend fun fetchBanks(
        type: BankType,
        localeTag: String,
        page: Int,
        pageSize: Int,
    ): BankFetchResult {
        val locatorPath = type.toRemoteLocationPath()

        val remoteResult =
            bankRemoteDataSource.getLocations(
                path = locatorPath,
                language = localeTag.toApiLang(),
                pageSize = pageSize,
                offset = page * pageSize,
            )

        return when (remoteResult) {
            BankRemoteResult.Error -> {
                BankFetchResult.Error
            }

            is BankRemoteResult.Success -> {
                remoteResult
                    .data
                    .map {
                        mapper.convertToLocal(
                            language = localeTag,
                            type = locatorPath,
                            locator = it,
                        )
                    }
                    .also { bankLocalDataSource.insertAll(it) }
                    .let { list ->
                        if (list.size < pageSize) {
                            BankFetchResult.End
                        } else {
                            BankFetchResult.HasNext
                        }
                    }
            }
        }
    }

    override suspend fun getBanksWithinBound(
        language: String,
        bound: BankLocationBound,
    ): List<BankModel> {
        return bankLocalDataSource.getBanksWithinBound(
            language = language.toLocalLanguage(),
            minLat = bound.minLat,
            maxLat = bound.maxLat,
            minLon = bound.minLong,
            maxLon = bound.maxLong,
        ).map(mapper::convertToDataModel)
    }

    override suspend fun getAllBanks(): List<String> {
        return bankLocalDataSource.getAllBanks()
    }

    override suspend fun getAll(): List<BankModel> {
        return bankLocalDataSource
            .getAll()
            .map(mapper::convertToDataModel)
    }
}
