package org.bl.coffeecounter.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import org.bl.coffeecounter.db.dao.CoffeDao
import org.bl.coffeecounter.db.entities.Coffee

class CoffeeRepository (private val coffeeDao: CoffeDao) {

    val allCoffee: Flow<List<Coffee>> = coffeeDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(coffee: Coffee) {
        coffeeDao.insert(coffee)
    }

    suspend fun reset() {
        coffeeDao.deleteAllCoffee()
    }


}