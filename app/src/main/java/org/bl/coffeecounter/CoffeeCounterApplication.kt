package org.bl.coffeecounter

import android.app.Application
import org.bl.coffeecounter.db.CoffeeDatabase
import org.bl.coffeecounter.db.CoffeeRepository

class CoffeeCounterApplication: Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { CoffeeDatabase.getDatabase(this) }
    val repository by lazy { CoffeeRepository(database.coffeeDao()) }

}