package ch.app.hk.bank.locator.feature.locator.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import ch.app.hk.bank.locator.feature.locator.data.local.entity.BankLocal

@Entity(
    tableName = "bank",
    primaryKeys = [
        "latitude",
        "longitude",
    ],
)
data class BankEntity(
    @ColumnInfo(name = "type")
    override val type: String,
    @ColumnInfo(name = "district")
    override val district: String,
    @ColumnInfo(name = "bank_name")
    override val bankName: String,
    @ColumnInfo(name = "type_name")
    override val typeName: String,
    @ColumnInfo(name = "address")
    override val address: String,
    @ColumnInfo(name = "service_hours")
    override val serviceHours: String,
    @ColumnInfo(name = "latitude")
    override val latitude: Double,
    @ColumnInfo(name = "longitude")
    override val longitude: Double,
) : BankLocal
