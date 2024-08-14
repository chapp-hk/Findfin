package ch.app.library.hiltext.binds.visitor

import com.google.devtools.ksp.symbol.KSAnnotation
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import dagger.hilt.components.SingletonComponent

data class HiltExtBindModel(
    val generatedClassPackageName: String = "",
    val generatedClassName: String = "",
    val implClass: ClassName? = null,
    val superClass: ClassName? = null,
    val installInComponent: ClassName = SingletonComponent::class.asClassName(),
    val annotations: List<KSAnnotation> = emptyList(),
)
