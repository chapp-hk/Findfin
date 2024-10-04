package org.chapp.findfin.feature.home.domain.nearby.model

data class Service(
    val type: String,
    val district: String,
    val bankName: String,
    val typeName: String,
    val address: String,
    val serviceHours: String,
    val latitude: Double,
    val longitude: Double,
)
