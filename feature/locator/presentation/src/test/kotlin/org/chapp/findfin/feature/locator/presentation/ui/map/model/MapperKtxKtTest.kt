package org.chapp.findfin.feature.locator.presentation.ui.map.model

import io.kotest.matchers.shouldBe
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.findfin.feature.locator.presentation.navigation.MapSearchType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@DisplayName("MapperKtx.Kt unit tests")
class MapperKtxKtTest {
    @ParameterizedTest
    @EnumSource(MapSearchType::class)
    fun `toBankType maps non-null types correctly`(input: MapSearchType) {
        val expected =
            when (input) {
                MapSearchType.BRANCH -> BankType.BRANCH
                MapSearchType.ATM -> BankType.ATM
            }

        input.toBankType() shouldBe expected
    }

    @Test
    fun `toBankType returns null for null input`() {
        val input: MapSearchType? = null
        input.toBankType() shouldBe null
    }
}
