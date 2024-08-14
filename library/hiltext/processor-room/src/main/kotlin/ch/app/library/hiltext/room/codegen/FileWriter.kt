package ch.app.library.hiltext.room.codegen

import ch.app.library.hiltext.room.visitor.HiltExtRoomModel
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.writeTo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

class FileWriter(
    private val codeGenerator: CodeGenerator,
    private val data: HiltExtRoomModel,
) {
    fun write() {
        FileSpec
            .builder(
                packageName = data.generatedClassPackageName,
                fileName = data.generatedClassName,
            )
            .addType(createType())
            .build()
            .writeTo(codeGenerator, Dependencies(true))
    }

    private fun createType(): TypeSpec {
        return TypeSpec
            .classBuilder(data.generatedClassName)
            .addAnnotation(Module::class)
            .addAnnotation(createInstallInAnnotation())
            .addModifiers(KModifier.INTERNAL)
            .addFunction(createDatabaseProviderFunction())
            .addFunctions(createDaoFunctionProviders())
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

    private fun createDatabaseProviderFunction(): FunSpec {
        val roomClass = ClassName("androidx.room", "Room")

        return FunSpec
            .builder("providesDatabase")
            .addAnnotation(Provides::class)
            .addParameter(createApplicationContextParameter())
            .returns(data.databaseClass)
            .addStatement(
                format = "return %T.databaseBuilder(context, %T::class.java, %S).build()",
                args = arrayOf(roomClass, data.databaseClass, data.databaseName),
            )
            .build()
    }

    private fun createApplicationContextParameter(): ParameterSpec {
        return ParameterSpec
            .builder(
                name = "context",
                type =
                    ClassName(
                        "android.content",
                        "Context",
                    ),
            )
            .addAnnotation(
                ClassName(
                    "dagger.hilt.android.qualifiers",
                    "ApplicationContext",
                ),
            )
            .build()
    }

    private fun createDaoFunctionProviders(): List<FunSpec> {
        val databaseParameterName = "database"

        return data.daoFunctionsProvider.map {
            FunSpec
                .builder("provide${it.returnType.simpleName}")
                .addAnnotation(Provides::class)
                .apply {
                    if (it.scopeClass != Nothing::class.asClassName()) {
                        addAnnotation(it.scopeClass)
                    }
                }
                .addParameter(databaseParameterName, data.databaseClass)
                .addStatement("return $databaseParameterName.${it.callableName}")
                .returns(it.returnType)
                .build()
        }
    }
}
