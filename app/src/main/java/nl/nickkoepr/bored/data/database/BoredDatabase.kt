package nl.nickkoepr.bored.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.nickkoepr.bored.model.Activity

@Database(exportSchema = false, entities = [Activity::class], version = 1)
abstract class BoredDatabase : RoomDatabase() {
    abstract fun boredDao(): BoredDao

    companion object {
        @Volatile
        private var INSTANCE: BoredDatabase? = null

        fun getDatabase(context: Context): BoredDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    BoredDatabase::class.java,
                    "bored_database"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}