package ch.app.hk.bank.locator.testing.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("FileUtil readResourceAsObject unit tests")
class FileUtilReadResourceAsObjectTest {
    data class TestEntity(
        val name: String,
        val module: String,
        val packageName: String,
        val version: Int,
    )

    @Test
    fun `test readResourceAsEntity`() {
        readResourceAsObject<TestEntity>("test-entity.json") shouldBe
            TestEntity(
                name = "test entity",
                module = ":test:room",
                packageName = "ch.app.hktransport.testing.room.entity",
                version = 1,
            )
    }

    @Test
    fun `test readResourceAsEntity json array`() {
        readResourceAsObject<List<TestEntity>>("test-entity-array.json") shouldBe
            listOf(
                TestEntity(
                    name = "test entity 1",
                    module = ":test:room",
                    packageName = "ch.app.hktransport.testing.room.entity",
                    version = 1,
                ),
                TestEntity(
                    name = "test entity 2",
                    module = ":test:room",
                    packageName = "ch.app.hktransport.testing.room.entity",
                    version = 1,
                ),
                TestEntity(
                    name = "test entity 3",
                    module = ":test:room",
                    packageName = "ch.app.hktransport.testing.room.entity",
                    version = 1,
                ),
            )
    }

    @Test
    fun `test readResourceAsEntity with invalid json`() {
        readResourceAsObject<TestEntity>("invalid.json") shouldBe null
    }
}
