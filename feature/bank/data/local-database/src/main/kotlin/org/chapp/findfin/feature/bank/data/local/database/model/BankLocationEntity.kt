package org.chapp.findfin.feature.bank.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "locator",
)
internal data class BankLocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "language")
    val language: String,
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
