package ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model

sealed interface Theme {
    data object Light : Theme

    data object Dark : Theme

    data object System : Theme
}
