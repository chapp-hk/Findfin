package org.chapp.lint.konsist

import com.lemonappdev.konsist.api.KoModifier
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.declaration.KoPropertyDeclaration
import com.lemonappdev.konsist.api.ext.list.indexOfFirstInstance
import com.lemonappdev.konsist.api.ext.list.indexOfLastInstance
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Konsist general tests")
class GeneralTest {
    @Test
    fun `properties are declared before functions`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue {
                val lastKoPropertyDeclarationIndex =
                    it
                        .declarations(includeNested = false, includeLocal = false)
                        .indexOfLastInstance<KoPropertyDeclaration>()

                val firstKoFunctionDeclarationIndex =
                    it
                        .declarations(includeNested = false, includeLocal = false)
                        .indexOfFirstInstance<KoFunctionDeclaration>()

                if (lastKoPropertyDeclarationIndex != -1 && firstKoFunctionDeclarationIndex != -1) {
                    lastKoPropertyDeclarationIndex < firstKoFunctionDeclarationIndex
                } else {
                    true
                }
            }
    }

    @Test
    fun `companion object is last declaration in the class`() {
        Konsist
            .scopeFromProject()
            .classes()
            .assertTrue {
                val companionObject =
                    it.objects(includeNested = false)
                        .lastOrNull { obj -> obj.hasModifier(KoModifier.COMPANION) }

                if (companionObject != null) {
                    it.declarations(
                        includeNested = false,
                        includeLocal = false,
                    ).last() == companionObject
                } else {
                    true
                }
            }
    }

    @Test
    fun `no empty files allowed`() {
        Konsist
            .scopeFromProject()
            .files
            .assertFalse { it.text.isEmpty() }
    }

    @Test
    fun `package name must match file path`() {
        Konsist
            .scopeFromProject()
            .packages
            .assertTrue { it.hasMatchingPath }
    }
}
