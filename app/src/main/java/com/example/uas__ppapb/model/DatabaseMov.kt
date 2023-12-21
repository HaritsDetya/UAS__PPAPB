package com.example.uas__ppapb.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [DataMovie::class], version = 1, exportSchema = false)
abstract class DatabaseMov : RoomDatabase(){
    abstract fun MovieFun(): MovieFun?
    companion object {
        @Volatile
        private var INSTANCE: DatabaseMov? = null
        private val sLock = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(sLock) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            databaseBuilder(context.applicationContext,
                DatabaseMov::class.java, "movie_database")
                .build()

        fun getDatabase(context: Context): DatabaseMov? {
            if (INSTANCE == null) {
                synchronized(DatabaseMov::class.java) {
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        DatabaseMov::class.java, "movie_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

    }
}