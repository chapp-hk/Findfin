package org.chapp.findfin.feature.bank.data.remote.network.datasource

import org.chapp.findfin.core.logging.appLogger
import org.chapp.findfin.feature.bank.data.remote.network.api.BankApi
import org.chapp.findfin.feature.bank.data.remote.network.mapper.toBankRemote
import org.chapp.findfin.feature.bank.data.remote.network.model.BankApiError
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.datasource.BankRemoteDataSource
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemoteResult
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.TypePath
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class BankRemoteDataSourceImpl @Inject constructor(
    private val bankApi: BankApi,
) : BankRemoteDataSource {
    override suspend fun getLocations(
        path: TypePath,
        language: String,
        pageSize: Int,
        offset: Int,
    ): BankRemoteResult {
        return runCatching {
            val response =
                bankApi.getLocations(
                    path = path.value,
                    lang = language,
                    pageSize = pageSize,
                    offset = offset,
                )

            if (response.header == null) {
                throw BankApiError(
                    errorCode = "",
                    errorMessage = "",
                )
            }

            if (response.header.success.not()) {
                throw BankApiError(
                    errorCode = response.header.errorCode,
                    errorMessage = response.header.errorMessage,
                )
            }

            BankRemoteResult.Success(response.result!!.records.map { it.toBankRemote() })
        }.getOrElse { error ->
            appLogger.debug(
                tag = javaClass.simpleName,
                message = "getLocators() failed",
                throwable = error,
            )
            BankRemoteResult.Error
        }
    }
}
