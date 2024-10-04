package org.chapp.findfin.testing.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("FileUtil readResourceAsText unit tests")
class FileUtilReadResourceAsTextTest {
    @Test
    fun `test readResourceAsText in resources dir root`() {
        readResourceAsText("test.txt") shouldBe "This is a test file in resource root dir."
    }

    @Test
    fun `test readResourceAsText in resources dir with path`() {
        readResourceAsText("path/test.txt") shouldBe "This is a test file in resource dir with path."
    }

    @Test
    fun `test readResourceAsText with invalid path`() {
        readResourceAsText("invalid.txt") shouldBe ""
    }
}
