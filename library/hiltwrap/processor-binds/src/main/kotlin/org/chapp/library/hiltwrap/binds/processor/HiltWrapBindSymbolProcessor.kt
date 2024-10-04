package org.chapp.library.hiltwrap.binds.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import org.chapp.library.hiltwrap.binds.codegen.FileWriter
import org.chapp.library.hiltwrap.binds.visitor.HiltWrapBindModel
import org.chapp.library.hiltwrap.binds.visitor.HiltWrapBindVisitor

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
