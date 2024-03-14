package ch.app.hk.bank.locator.core.design.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Shape unit tests")
class ShapeTest {
    @Test
    fun `test Shapes`() {
        Shapes.apply {
            small shouldBe RoundedCornerShape(4.dp)
            medium shouldBe RoundedCornerShape(4.dp)
            large shouldBe RoundedCornerShape(0.dp)
        }
    }
}
