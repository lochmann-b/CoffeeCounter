package org.bl.coffeecounter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.bl.coffeecounter.db.dao.CoffeDao
import org.bl.coffeecounter.db.entities.Coffee

@Database(entities = arrayOf(Coffee::class), version = 1)
abstract class CoffeeDatabase: RoomDatabase() {

    abstract fun coffeeDao(): CoffeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CoffeeDatabase? = null

        fun getDatabase(context: Context): CoffeeDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoffeeDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}