package org.bl.coffeecounter.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.bl.coffeecounter.db.entities.Coffee

@Dao
interface CoffeDao {
    @Query("Select * from coffee")
    fun getAll(): Flow<List<Coffee>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend  fun insert(coffee: Coffee)

    @Query("DELETE FROM coffee")
    suspend fun deleteAllCoffee()
}