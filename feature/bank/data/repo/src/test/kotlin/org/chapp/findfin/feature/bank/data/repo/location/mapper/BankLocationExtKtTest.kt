package org.chapp.findfin.feature.bank.data.repo.location.mapper

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.remote.location.api.LocationPath
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankLocationExt unit tests")
class BankLocationExtKtTest {
    @Test
    fun `test toRemoteLocatorPath`() {
        BankLocationType.ATM.toRemoteLocationPath() shouldBe LocationPath.ATM
        BankLocationType.BRANCH.toRemoteLocationPath() shouldBe LocationPath.BRANCH
    }

    @Test
    fun `test toApiLang`() {
        "zh".toApiLang() shouldBe "tc"
        "en".toApiLang() shouldBe "en"
        "".toApiLang() shouldBe "en"
    }
}
