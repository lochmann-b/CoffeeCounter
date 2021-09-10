package org.bl.coffeecounter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.bl.coffeecounter.db.converters.Converters
import org.bl.coffeecounter.db.dao.CoffeDao
import org.bl.coffeecounter.db.dao.PaymentDao
import org.bl.coffeecounter.db.entities.Coffee
import org.bl.coffeecounter.db.entities.Payment

@Database(entities = [Coffee::class, Payment::class], version = 1)
@TypeConverters(Converters::class)
abstract class CoffeeDatabase: RoomDatabase() {

    abstract fun coffeeDao(): CoffeDao
    abstract fun paymentDao(): PaymentDao

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