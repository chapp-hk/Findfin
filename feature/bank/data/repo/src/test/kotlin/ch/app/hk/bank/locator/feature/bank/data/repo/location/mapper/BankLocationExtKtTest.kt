package ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocatorExt unit tests")
class BankLocationExtKtTest {
    @Test
    fun `test toRemoteLocatorPath`() {
        BankLocationType.ATM.toRemoteLocatorPath() shouldBe LocatorPath.ATM
        BankLocationType.BRANCH.toRemoteLocatorPath() shouldBe LocatorPath.BRANCH
    }

    @Test
    fun `test toApiLang`() {
        "zh".toApiLang() shouldBe "tc"
        "en".toApiLang() shouldBe "en"
        "".toApiLang() shouldBe "en"
    }
}
