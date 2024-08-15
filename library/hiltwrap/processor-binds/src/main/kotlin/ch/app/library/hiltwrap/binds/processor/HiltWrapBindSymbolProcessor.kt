package ch.app.library.hiltwrap.binds.processor

import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import ch.app.library.hiltwrap.binds.codegen.FileWriter
import ch.app.library.hiltwrap.binds.visitor.HiltWrapBindModel
import ch.app.library.hiltwrap.binds.visitor.HiltWrapBindVisitor
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate

class HiltWrapBindSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.getSymbolsWithAnnotation(HiltWrapBindModule::class.java.name)
            .filterIsInstance<KSClassDeclaration>()
            .also { classDeclaration ->
                classDeclaration.forEach {
                    FileWriter(
                        codeGenerator = codeGenerator,
                        data = it.accept(HiltWrapBindVisitor(logger), HiltWrapBindModel()),
                    ).write()
                }
            }
            .filterNot { it.validate() }
            .toList()
    }
}
