package org.chapp.library.hiltwrap.annotation

import kotlin.reflect.KClass

/**
 * Annotation to mark a class as a Hilt module for Room database integration.
 *
 * This annotation is used to generate Hilt modules that bind Room database components.
 *
 * @property installInComponent The Hilt component in which the module will be installed.
 * @property databaseName The name of the Room database.
 */
@Target(AnnotationTarget.CLASS)
annotation class HiltWrapRoomModule(
    val installInComponent: KClass<*> = Nothing::class,
    val databaseName: String,
)

/**
 * Annotation to mark a property or function as a Room DAO for Hilt dependency injection.
 *
 * This annotation is used to generate Hilt modules that bind Room DAO components.
 *
 * @property scope The Hilt scope in which the DAO will be provided.
 */
@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
)
annotation class HiltWrapRoomDao(
    val scope: KClass<*> = Nothing::class,
)
