package ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocationPath
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType
import io.kotest.matchers.shouldBe
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
