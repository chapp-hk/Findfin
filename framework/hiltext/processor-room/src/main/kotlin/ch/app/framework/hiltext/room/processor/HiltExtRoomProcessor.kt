package ch.app.framework.hiltext.room.processor

import ch.app.framework.hiltext.annotation.HiltExtRoomModule
import ch.app.framework.hiltext.room.codegen.FileWriter
import ch.app.framework.hiltext.room.visitor.HiltExtRoomModel
import ch.app.framework.hiltext.room.visitor.HiltExtRoomVisitor
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate

class HiltExtRoomProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        return resolver.getSymbolsWithAnnotation(HiltExtRoomModule::class.java.name)
            .filterIsInstance<KSClassDeclaration>()
            .also { classDeclaration ->
                classDeclaration.forEach {
                    FileWriter(
                        codeGenerator = codeGenerator,
                        data = it.accept(HiltExtRoomVisitor(logger), HiltExtRoomModel()),
                    ).write()
                }
            }
            .filterNot { it.validate() }
            .toList()
    }
}
