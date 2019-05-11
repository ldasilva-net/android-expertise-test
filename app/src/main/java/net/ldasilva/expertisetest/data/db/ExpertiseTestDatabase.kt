package net.ldasilva.expertisetest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.ldasilva.expertisetest.data.db.entity.MovieEntry

@Database(
    entities = [MovieEntry::class],
    version = 1
)

abstract class ExpertiseTestDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile private var instance: ExpertiseTestDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    ExpertiseTestDatabase::class.java, "expertiseTest.db")
                    .build()
    }
}