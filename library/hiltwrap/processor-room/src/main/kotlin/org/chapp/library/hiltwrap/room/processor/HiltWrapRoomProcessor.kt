package org.chapp.library.hiltwrap.room.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import org.chapp.library.hiltwrap.annotation.HiltWrapRoomModule
import org.chapp.library.hiltwrap.room.codegen.FileWriter
import org.chapp.library.hiltwrap.room.visitor.HiltWrapRoomModel
import org.chapp.library.hiltwrap.room.visitor.HiltWrapRoomVisitor

class HiltWrapRoomProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.getSymbolsWithAnnotation(HiltWrapRoomModule::class.java.name)
            .filterIsInstance<KSClassDeclaration>()
            .also { classDeclaration ->
                classDeclaration.forEach {
                    FileWriter(
                        codeGenerator = codeGenerator,
                        data = it.accept(HiltWrapRoomVisitor(logger), HiltWrapRoomModel()),
                    ).write()
                }
            }
            .filterNot { it.validate() }
            .toList()
    }
}
