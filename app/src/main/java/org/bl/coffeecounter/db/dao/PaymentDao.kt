package org.bl.coffeecounter.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.bl.coffeecounter.db.entities.Payment


@Dao
interface PaymentDao {
    @Query("select * from payment")
    fun getAll(): Flow<List<Payment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(payment: Payment)

    @Delete
    suspend fun delete(payment: Payment)

}