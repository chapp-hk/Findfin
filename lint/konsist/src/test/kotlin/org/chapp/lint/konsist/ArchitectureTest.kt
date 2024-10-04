package org.chapp.lint.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Konsist architecture tests")
class ArchitectureTest {
    @Test
    fun `'src' directory should be 'kotlin'`() {
        Konsist
            .scopeFromProject()
            .files
            .assertTrue { file ->
                file.path.contains("src/\\w+/kotlin".toRegex())
            }
    }

    @Test
    fun `classes with 'UseCase' suffix should have single 'public operator' method named 'invoke'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue {
                val hasPublicInvokeOperatorMethod =
                    it.hasFunction { function ->
                        function.name == "invoke" && function.hasPublicOrDefaultModifier && function.hasOperatorModifier
                    }

                val hasOnlyOnePublicMethod = it.countFunctions { item -> item.hasPublicOrDefaultModifier } == 1

                hasPublicInvokeOperatorMethod && hasOnlyOnePublicMethod
            }
    }
}
