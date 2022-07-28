package com.samuel.embriotechnicaltest.business.datasource.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

    companion object {
        // TODO: Create thread-safe singleton
        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return if (instance == null) {
                val localInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appdb"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = localInstance
                localInstance
            } else {
                instance!!
            }
        }

    }

}