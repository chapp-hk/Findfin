package ch.app.framework.hiltext.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class HiltExtBindModule(
    val superType: KClass<*> = Nothing::class,
    val component: KClass<*> = Nothing::class,
)
