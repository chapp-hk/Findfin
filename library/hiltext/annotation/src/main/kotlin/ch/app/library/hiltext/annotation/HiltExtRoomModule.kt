package ch.app.library.hiltext.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class HiltExtRoomModule(
    val installInComponent: KClass<*> = Nothing::class,
    val databaseName: String,
)

@Target(
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
)
annotation class HiltExtRoomDao(
    val scope: KClass<*> = Nothing::class,
)
