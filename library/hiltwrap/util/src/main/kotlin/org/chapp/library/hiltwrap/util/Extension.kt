package org.chapp.library.hiltwrap.util

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toClassName
import kotlin.reflect.KClass

fun KSAnnotated.findAnnotation(klass: KClass<*>): KSAnnotation {
    return annotations
        .filter { it.annotationType.resolve().toClassName() == klass.asClassName() }
        .first()
}

inline fun <reified T> KSAnnotation.findNamedValue(name: String): T {
    return arguments.find { it.name?.asString() == name }!!.value!! as T
}

fun KSType.isNothing(): Boolean {
    return toClassName() == Nothing::class.asClassName()
}
