package ch.app.library.hiltwrap.binds.codegen

import ch.app.library.hiltwrap.binds.visitor.HiltWrapBindModel
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.writeTo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

class FileWriter(
    private val codeGenerator: CodeGenerator,
    private val data: HiltWrapBindModel,
) {
    fun write() {
        FileSpec
            .builder(
                packageName = data.generatedClassPackageName,
                fileName = data.generatedClassName,
            )
            .addType(createType())
            .indent("    ")
            .build()
            .writeTo(codeGenerator, Dependencies(true))
    }

    private fun createType(): TypeSpec {
        return TypeSpec
            .interfaceBuilder(data.generatedClassName)
            .addAnnotation(Module::class)
            .addAnnotation(createInstallInAnnotation())
            .addModifiers(KModifier.INTERNAL)
            .addFunction(createBindsFunction())
            .build()
    }

    private fun createInstallInAnnotation(): AnnotationSpec {
        return AnnotationSpec
            .builder(InstallIn::class)
            .addMember(
                "%T::class",
                data.installInComponent,
            )
            .build()
    }

    private fun createBindsFunction(): FunSpec {
        return FunSpec
            .builder("bindToSuperType")
            .addAnnotation(Binds::class)
            .addAnnotations(data.annotations.map { it.toAnnotationSpec() })
            .addModifiers(KModifier.ABSTRACT)
            .addParameter(
                name = "impl",
                type = data.implClass!!,
            )
            .returns(data.superClass!!)
            .build()
    }
}
