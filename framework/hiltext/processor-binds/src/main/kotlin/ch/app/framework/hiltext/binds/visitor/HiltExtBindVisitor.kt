package ch.app.framework.hiltext.binds.visitor

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.framework.hiltext.util.findAnnotation
import ch.app.framework.hiltext.util.findNamedValue
import ch.app.framework.hiltext.util.isNothing
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getConstructors
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent
import java.lang.instrument.IllegalClassFormatException
import javax.inject.Inject

class HiltExtBindVisitor(
    private val logger: KSPLogger,
) : KSEmptyVisitor<HiltExtBindModel, HiltExtBindModel>() {
    override fun defaultHandler(
        node: KSNode,
        data: HiltExtBindModel,
    ): HiltExtBindModel {
        logger.info("processing data: $data")
        return data
    }

    override fun visitClassDeclaration(
        classDeclaration: KSClassDeclaration,
        data: HiltExtBindModel,
    ): HiltExtBindModel {
        assertHasInjectAnnotation(classDeclaration)

        val annotation = classDeclaration.findAnnotation(HiltExtBindModule::class)
        val newData =
            HiltExtBindModel(
                generatedClassPackageName = classDeclaration.packageName.asString(),
                generatedClassName = "${classDeclaration.simpleName.asString()}HiltExtBindModule",
                implClass = classDeclaration.toClassName(),
                superClass = getSuperClass(annotation),
                installInComponent = getInstallInComponent(annotation),
                annotations =
                    classDeclaration.annotations
                        .filter { it.annotationType.toTypeName() != HiltExtBindModule::class.asClassName() }
                        .toList(),
            )
        return super.visitClassDeclaration(classDeclaration, newData)
    }

    private fun getSuperClass(annotation: KSAnnotation): ClassName {
        val superTypeArgument = annotation.findNamedValue<KSType>("superType")

        if (superTypeArgument.isNothing()) {
            return (annotation.parent as KSClassDeclaration)
                .superTypes
                .map { it.resolve().toClassName() }
                .filterNot { it == Any::class.asTypeName() }
                .first()
        }

        assertAssignableToSuperType(annotation, superTypeArgument)
        return superTypeArgument.toClassName()
    }

    private fun getInstallInComponent(annotation: KSAnnotation): ClassName {
        val component = annotation.findNamedValue<KSType>("component")

        if (component.isNothing()) {
            return SingletonComponent::class.asClassName()
        }

        assertInstallInComponentAnnotation(component)
        return component.toClassName()
    }

    @OptIn(KspExperimental::class)
    private fun assertHasInjectAnnotation(classDeclaration: KSClassDeclaration) {
        classDeclaration
            .getConstructors()
            .any { it.isAnnotationPresent(Inject::class) }
            .let { hasInjectAnnotation ->
                if (hasInjectAnnotation.not()) {
                    throw IllegalClassFormatException(
                        "${classDeclaration.qualifiedName?.asString()} must annotated with @Inject",
                    )
                }
            }
    }

    @OptIn(KspExperimental::class)
    private fun assertInstallInComponentAnnotation(installInComponent: KSType) {
        installInComponent
            .declaration
            .isAnnotationPresent(DefineComponent::class)
            .let { isInstallInComponentAnnotated ->
                if (isInstallInComponentAnnotated.not()) {
                    throw IllegalClassFormatException(
                        "$installInComponent should be annotated with @DefineComponent",
                    )
                }
            }
    }

    private fun assertAssignableToSuperType(
        annotation: KSAnnotation,
        superType: KSType,
    ) {
        (annotation.parent as KSClassDeclaration)
            .getAllSuperTypes()
            .contains(superType)
            .let { isAssignableToSuperType ->
                if (isAssignableToSuperType.not()) {
                    throw IllegalClassFormatException(
                        "${annotation.parent} is not assignable to $superType",
                    )
                }
            }
    }
}
