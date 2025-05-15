package org.chapp.findfin.feature.bank.data.repo.repository

import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.feature.bank.data.repo.datasource.local.datasource.BankLocalDataSource
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankQueryParameters
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.datasource.BankRemoteDataSource
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemoteResult
import org.chapp.findfin.feature.bank.data.repo.mapper.toApiLang
import org.chapp.findfin.feature.bank.data.repo.mapper.toBankLocal
import org.chapp.findfin.feature.bank.data.repo.mapper.toBankModel
import org.chapp.findfin.feature.bank.data.repo.mapper.toLocalLanguage
import org.chapp.findfin.feature.bank.data.repo.mapper.toRemoteLocationPath
import org.chapp.findfin.feature.bank.data.repo.model.BankFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class BankRepositoryImpl @Inject constructor(
    private val localeProviderManager: LocaleProviderManager,
    private val bankLocalDataSource: BankLocalDataSource,
    private val bankRemoteDataSource: BankRemoteDataSource,
) : BankRepository {
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
                        it.toBankLocal(
                            language = localeTag,
                            type = locatorPath,
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

    override suspend fun getAllBanks(): List<String> {
        return bankLocalDataSource.getAllBanks(language = localeProviderManager.getCurrentLocaleTag())
    }

    override suspend fun getBanksByParameters(
        name: String?,
        type: BankType?,
        bound: BankLocationBound?,
    ): List<BankModel> {
        val params =
            BankQueryParameters(
                language = localeProviderManager.getCurrentLocaleTag().toLocalLanguage(),
                bankName = name,
                type = type?.name,
                minLatitude = bound?.minLatitude,
                maxLatitude = bound?.maxLatitude,
                minLongitude = bound?.minLongitude,
                maxLongitude = bound?.maxLongitude,
            )

        return bankLocalDataSource.getBanksWithParameters(params).map { it.toBankModel() }
    }
}
