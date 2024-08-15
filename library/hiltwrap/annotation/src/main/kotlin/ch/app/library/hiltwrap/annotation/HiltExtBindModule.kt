package ch.app.library.hiltwrap.annotation

import kotlin.reflect.KClass

/**
 * Annotation to mark a class as a Hilt module for binding dependencies.
 *
 * This annotation is used to generate Hilt modules that bind a specific type to its implementation.
 *
 * @property superType The super type or interface that the annotated class will bind to.
 * @property component The Hilt component in which the module will be installed.
 */
@Target(AnnotationTarget.CLASS)
annotation class HiltExtBindModule(
    val superType: KClass<*> = Nothing::class,
    val component: KClass<*> = Nothing::class,
)
