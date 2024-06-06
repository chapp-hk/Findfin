package ch.app.hk.bank.locator.feature.locator.data.repo.model

import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.locator.data.repo.mapper.toApiLang
import ch.app.hk.bank.locator.feature.locator.data.repo.mapper.toRemoteLocatorPath
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocatorExt unit tests")
class LocatorTypeExtKtTest {
    @Test
    fun `test toRemoteLocatorPath`() {
        LocatorType.ATM.toRemoteLocatorPath() shouldBe LocatorPath.ATM
        LocatorType.BRANCH.toRemoteLocatorPath() shouldBe LocatorPath.BRANCH
    }

    @Test
    fun `test toApiLang`() {
        "zh".toApiLang() shouldBe "tc"
        "en".toApiLang() shouldBe "en"
        "".toApiLang() shouldBe "en"
    }
}
