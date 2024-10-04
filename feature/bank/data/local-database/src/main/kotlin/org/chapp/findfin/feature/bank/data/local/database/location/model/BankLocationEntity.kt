package org.chapp.findfin.feature.bank.data.local.database.location.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "locator",
    primaryKeys = [
        "latitude",
        "longitude",
    ],
)
internal data class BankLocationEntity(
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "district")
    val district: String,
    @ColumnInfo(name = "bank_name")
    val bankName: String,
    @ColumnInfo(name = "type_name")
    val typeName: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "service_hours")
    val serviceHours: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
)
