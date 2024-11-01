package org.chapp.findfin.feature.setting.data.repo.language.repository

import org.chapp.findfin.feature.setting.data.repo.language.model.Language
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import java.util.Locale
import javax.inject.Inject

@HiltWrapBindModule
internal class LanguageRepositoryImpl @Inject constructor() : LanguageRepository {
    override fun getAvailableLanguages(): List<Language> {
        return listOf("", "en", "zh").map { languageTag ->
            Language(
                isDefault = languageTag.isEmpty(),
                name = Locale.forLanguageTag(languageTag).let { it.getDisplayName(it) },
                tag = languageTag,
            )
        }
    }
}
