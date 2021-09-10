package org.bl.coffeecounter

import android.app.Application
import org.bl.coffeecounter.db.CoffeeDatabase
import org.bl.coffeecounter.db.CoffeeRepository

class CoffeeCounterApplication: Application() {
    val database by lazy { CoffeeDatabase.getDatabase(this) }
    val repository by lazy { CoffeeRepository(database.coffeeDao(), database.paymentDao()) }
}