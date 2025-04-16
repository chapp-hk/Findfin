package org.chapp.findfin.feature.bank.data.repo.mapper

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.remote.network.api.LocationPath
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankLocationExt unit tests")
class BankDataExtKtTest {
    @Test
    fun `test toRemoteLocatorPath`() {
        BankType.ATM.toRemoteLocationPath() shouldBe LocationPath.ATM
        BankType.BRANCH.toRemoteLocationPath() shouldBe LocationPath.BRANCH
    }

    @Test
    fun `test toApiLang`() {
        "zh".toApiLang() shouldBe "tc"
        "en".toApiLang() shouldBe "en"
        "".toApiLang() shouldBe "en"
    }

    @Test
    fun `test toLocalLanguage`() {
        "zh".toLocalLanguage() shouldBe "zh"
        "en".toLocalLanguage() shouldBe "en"
        "jp".toLocalLanguage() shouldBe "en"
        "fr".toLocalLanguage() shouldBe "en"
    }
}
