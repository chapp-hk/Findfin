package org.chapp.findfin.core.locale.api

sealed interface LocaleResult {
    data object Error : LocaleResult

    data object Default : LocaleResult

    data class Custom(
        val tag: String,
        val displayName: String,
    ) : LocaleResult
}
