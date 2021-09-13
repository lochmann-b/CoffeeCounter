package org.bl.coffeecounter.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Coffee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name= "date_time") val localDateTime: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name="is_synced") val synced: Boolean = false,
    @ColumnInfo(name="flavour") val flavour: String = "-",
    @ColumnInfo(name="cost") val cost: Byte = 50,
    @ColumnInfo(name="from_nfc") val fromNFC: Boolean
)