package ch.app.framework.hiltext.room.visitor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import dagger.hilt.components.SingletonComponent

data class HiltExtRoomModel(
    val generatedClassPackageName: String = "",
    val generatedClassName: String = "",
    val databaseClass: ClassName = Nothing::class.asClassName(),
    val databaseName: String = "",
    val installInComponent: ClassName = SingletonComponent::class.asClassName(),
    val daoFunctionsProvider: List<HiltExtRoomDaoModel> = listOf(),
)

data class HiltExtRoomDaoModel(
    val callableName: String,
    val returnType: ClassName,
    val scopeClass: ClassName = Nothing::class.asClassName(),
)
