package org.chapp.library.hiltwrap.room.visitor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import dagger.hilt.components.SingletonComponent

data class HiltWrapRoomModel(
    val generatedClassPackageName: String = "",
    val generatedClassName: String = "",
    val databaseClass: ClassName = Nothing::class.asClassName(),
    val databaseName: String = "",
    val installInComponent: ClassName = SingletonComponent::class.asClassName(),
    val daoFunctionsProvider: List<HiltWrapRoomDaoModel> = listOf(),
)

data class HiltWrapRoomDaoModel(
    val callableName: String,
    val returnType: ClassName,
    val scopeClass: ClassName = Nothing::class.asClassName(),
)
