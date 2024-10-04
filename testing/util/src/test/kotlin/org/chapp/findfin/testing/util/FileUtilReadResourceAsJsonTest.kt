package org.chapp.findfin.testing.util

import io.kotest.matchers.shouldBe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("FileUtil readResourceAsObject unit tests")
class FileUtilReadResourceAsJsonTest {
    @Test
    fun `test readResourceAsJson with all valid fields`() {
        @Serializable
        data class TestData(
            @SerialName("name")
            val name: String,
            @SerialName("module")
            val module: String,
            @SerialName("package_name")
            val packageName: String,
            @SerialName("version")
            val version: Int,
        )

        readResourceAsJson<TestData>("json/test-data-valid.json") shouldBe
            TestData(
                name = "test data",
                module = ":testing:util",
                packageName = "org.chapp.findfin",
                version = 1,
            )
    }

    @Test
    fun `test readResourceAsJson with missing field in json string`() {
        @Serializable
        data class TestData(
            @SerialName("name")
            val name: String,
            @SerialName("module")
            val module: String?,
            @SerialName("package_name")
            val packageName: String,
            @SerialName("version")
            val version: Int,
        )

        readResourceAsJson<TestData>("json/test-data-missing-field.json") shouldBe
            TestData(
                name = "test data",
                module = null,
                packageName = "org.chapp.findfin",
                version = 1,
            )
    }

    @Test
    fun `test readResourceAsJson with extra field in json string, should ignore the extra field`() {
        @Serializable
        data class TestData(
            @SerialName("name")
            val name: String,
            @SerialName("module")
            val module: String,
            @SerialName("package_name")
            val packageName: String,
            @SerialName("version")
            val version: Int,
        )

        readResourceAsJson<TestData>("json/test-data-extra-field.json") shouldBe
            TestData(
                name = "test data",
                module = ":testing:util",
                packageName = "org.chapp.findfin",
                version = 1,
            )
    }
}
