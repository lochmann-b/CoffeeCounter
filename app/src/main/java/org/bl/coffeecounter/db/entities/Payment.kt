package org.bl.coffeecounter.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "payment")
data class Payment (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name= "date_time") val localDateTime: LocalDateTime,
    val amount: Int
)
