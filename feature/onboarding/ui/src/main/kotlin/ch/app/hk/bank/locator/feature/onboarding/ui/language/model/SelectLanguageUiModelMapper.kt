package ch.app.hk.bank.locator.feature.onboarding.ui.language.model

import ch.app.hk.bank.locator.core.locale.api.AppLocale
import org.mapstruct.Mapper

@Mapper
interface SelectLanguageUiModelMapper {
    fun clone(appLocale: AppLocale): SelectLanguageUiModel
}
