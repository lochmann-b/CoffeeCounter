package org.bl.coffeecounter.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coffee(
    @PrimaryKey val timestamp: Long,
    @ColumnInfo(name="is_synced") val synced: Boolean = false,
    @ColumnInfo(name="coffee_type") val coffeeType: String = "-"

)
