package org.chapp.findfin.core.location.provider.api

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Location unit tests")
class LocationTest {
    @Test
    fun `test fields`() {
        val location = Location(1.0, 2.0)
        location.latitude shouldBe 1.0
        location.longitude shouldBe 2.0
    }
}
