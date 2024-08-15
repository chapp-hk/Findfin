package ch.app.library.hiltwrap.room.visitor

import androidx.room.Database
import ch.app.library.hiltwrap.annotation.HiltExtRoomDao
import ch.app.library.hiltwrap.annotation.HiltExtRoomModule
import ch.app.library.hiltwrap.util.findAnnotation
import ch.app.library.hiltwrap.util.findNamedValue
import ch.app.library.hiltwrap.util.isNothing
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toClassName
import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent
import java.lang.instrument.IllegalClassFormatException
import javax.inject.Scope

class HiltExtRoomVisitor(
    private val logger: KSPLogger,
) : KSEmptyVisitor<HiltExtRoomModel, HiltExtRoomModel>() {
    override fun defaultHandler(
        node: KSNode,
        data: HiltExtRoomModel,
    ): HiltExtRoomModel {
        logger.info(data.toString(), node)
        return data
    }

    override fun visitClassDeclaration(
        classDeclaration: KSClassDeclaration,
        data: HiltExtRoomModel,
    ): HiltExtRoomModel {
        assertDatabaseAnnotationPresent(classDeclaration)

        val annotation = classDeclaration.findAnnotation(HiltExtRoomModule::class)
        val newData =
            HiltExtRoomModel(
                generatedClassPackageName = classDeclaration.packageName.asString(),
                generatedClassName = "${classDeclaration.simpleName.asString()}HiltExtRoomModule",
                databaseClass = classDeclaration.toClassName(),
                databaseName = getDatabaseName(annotation),
                installInComponent = getInstallInComponent(annotation),
                daoFunctionsProvider = getDaoProviderFunctionModels(classDeclaration),
            )

        return super.visitClassDeclaration(classDeclaration, newData)
    }

    @OptIn(KspExperimental::class)
    private fun assertDatabaseAnnotationPresent(classDeclaration: KSClassDeclaration) {
        if (classDeclaration.isAnnotationPresent(Database::class).not()) {
            throw IllegalClassFormatException(
                "${classDeclaration.simpleName.asString()} should be annotated with @Database",
            )
        }
    }

    private fun getDatabaseName(annotation: KSAnnotation): String {
        val databaseName = annotation.findNamedValue<String>("databaseName")

        require(databaseName.isNotBlank()) {
            "databaseName should not be empty"
        }

        return databaseName
    }

    @OptIn(KspExperimental::class)
    private fun getInstallInComponent(annotation: KSAnnotation): ClassName {
        val component = annotation.findNamedValue<KSType>("installInComponent")

        if (component.isNothing()) {
            return SingletonComponent::class.asClassName()
        }

        if (component.declaration.isAnnotationPresent(DefineComponent::class).not()) {
            throw IllegalClassFormatException(
                "$component should be annotated with @DefineComponent",
            )
        }

        return component.toClassName()
    }

    @OptIn(KspExperimental::class)
    private fun getDaoProviderFunctionModels(classDeclaration: KSClassDeclaration): List<HiltExtRoomDaoModel> {
        val daoFunctions =
            classDeclaration
                .getAllFunctions()
                .filter { it.isAnnotationPresent(HiltExtRoomDao::class) }
                .map {
                    HiltExtRoomDaoModel(
                        callableName = "${it.simpleName.asString()}()",
                        returnType = it.returnType?.resolve()?.toClassName()!!,
                        scopeClass = getScope(it.findAnnotation(HiltExtRoomDao::class)),
                    )
                }
                .toList()

        val daoProperties =
            classDeclaration
                .getAllProperties()
                .filter { it.isAnnotationPresent(HiltExtRoomDao::class) }
                .map {
                    HiltExtRoomDaoModel(
                        callableName = it.simpleName.asString(),
                        returnType = it.type.resolve().toClassName(),
                        scopeClass = getScope(it.findAnnotation(HiltExtRoomDao::class)),
                    )
                }
                .toList()

        return daoFunctions + daoProperties
    }

    @OptIn(KspExperimental::class)
    private fun getScope(annotation: KSAnnotation): ClassName {
        val scope = annotation.findNamedValue<KSType>("scope")

        if (scope.isNothing().not() && scope.declaration.isAnnotationPresent(Scope::class).not()) {
            throw IllegalClassFormatException(
                "$scope should be annotated with @Scope",
            )
        }

        return scope.toClassName()
    }
}
