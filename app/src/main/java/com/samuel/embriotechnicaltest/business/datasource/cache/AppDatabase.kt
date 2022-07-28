package com.samuel.embriotechnicaltest.business.datasource.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            instance?.let {
                val localInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appdb"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = localInstance
                return localInstance
            } ?: return instance!!
        }

    }

}