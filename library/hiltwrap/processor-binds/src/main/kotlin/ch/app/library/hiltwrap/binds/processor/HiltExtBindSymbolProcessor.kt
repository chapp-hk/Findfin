package ch.app.library.hiltwrap.binds.processor

import ch.app.library.hiltwrap.annotation.HiltExtBindModule
import ch.app.library.hiltwrap.binds.codegen.FileWriter
import ch.app.library.hiltwrap.binds.visitor.HiltExtBindModel
import ch.app.library.hiltwrap.binds.visitor.HiltExtBindVisitor
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate

class HiltExtBindSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.getSymbolsWithAnnotation(HiltExtBindModule::class.java.name)
            .filterIsInstance<KSClassDeclaration>()
            .also { classDeclaration ->
                classDeclaration.forEach {
                    FileWriter(
                        codeGenerator = codeGenerator,
                        data = it.accept(HiltExtBindVisitor(logger), HiltExtBindModel()),
                    ).write()
                }
            }
            .filterNot { it.validate() }
            .toList()
    }
}
