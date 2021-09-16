package org.bl.coffeecounter.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import org.bl.coffeecounter.db.dao.CoffeDao
import org.bl.coffeecounter.db.dao.PaymentDao
import org.bl.coffeecounter.db.entities.Coffee
import org.bl.coffeecounter.db.entities.Payment

class CoffeeRepository (private val coffeeDao: CoffeDao, private val paymentDao: PaymentDao) {

    val allCoffee: Flow<List<Coffee>> = coffeeDao.getAll()
    val allPayment: Flow<List<Payment>> = paymentDao.getAll()

    @WorkerThread
    suspend fun insert(coffee: Coffee) {
        coffeeDao.insert(coffee)
    }

    @WorkerThread
    suspend fun insert(payment: Payment) {
        paymentDao.insert(payment)
    }

    @WorkerThread
    suspend fun delete(id: Int) {
        paymentDao.delete(id)
    }

    @WorkerThread
    suspend fun deleteCoffee(id: Int) {
        coffeeDao.delete(id)
    }

}