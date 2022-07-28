package com.samuel.embriotechnicaltest.business.datasource.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // we will only save the latest weather report
    suspend fun insertWeather(video: WeatherEntity): Long // returns row number on success, -1 on failure

    @Query("SELECT * FROM weather ORDER BY pk ASC")
    suspend fun getWeatherList(): List<WeatherEntity>

}