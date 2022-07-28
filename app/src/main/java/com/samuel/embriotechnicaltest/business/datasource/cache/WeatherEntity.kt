package com.samuel.embriotechnicaltest.business.datasource.cache

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samuel.embriotechnicaltest.business.domain.models.Weather
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

private val TAG: String = "WeatherEntityDebug"

@Entity(tableName = "weather")
data class WeatherEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    val dayIndex: Int,

    @ColumnInfo(name = "day")
    val day: String,

    @ColumnInfo(name = "temp")
    val temp: String,

    @ColumnInfo(name = "feelsLike")
    val feelsLike: String,

    @ColumnInfo(name = "minTemp")
    val minTemp: String,

    @ColumnInfo(name = "maxTemp")
    val maxTemp: String,

    @ColumnInfo(name = "humidity")
    val humidity: String,

    @ColumnInfo(name = "desc")
    val desc: String,

    @ColumnInfo(name = "main_desc")
    val main_desc: String,

)

fun WeatherEntity.toWeather(): Weather {
    return Weather(
        day = day,
        temp = temp,
        feelsLike = feelsLike,
        minTemp = minTemp,
        maxTemp = maxTemp,
        humidity = humidity,
        desc = desc,
        main_desc = main_desc
    )
}

fun fromOWMResponse(response: StringBuffer, i: Int): WeatherEntity {
    val list = JSONObject(response.toString()).getJSONArray("list")
    val dayObject = list.getJSONObject(i)
    val mainObject = dayObject.getJSONObject("main")
    val weatherObject = dayObject.getJSONArray("weather").getJSONObject(0)
    return WeatherEntity(
        dayIndex = i,
        day = getDateTime(dayObject.getString("dt")),
        temp = String.format("%.1f", (mainObject.getString("temp").toFloat() - 273.15)),
        feelsLike = String.format("%.1f", (mainObject.getString("feels_like").toFloat() - 273.15)),
        minTemp = String.format("%.1f", (mainObject.getString("temp_min").toFloat() - 273.15)),
        maxTemp = String.format("%.1f", (mainObject.getString("temp_max").toFloat() - 273.15)),
        humidity = mainObject.getString("humidity"),
        desc = weatherObject.getString("description"),
        main_desc = weatherObject.getString("main"),
    )
}

private fun getDateTime(s: String): String {
    return try {
        val sdf = SimpleDateFormat("EEEE")
        val netDate = Date(s.toLong() * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        Log.e(TAG, "getDateTime: Error parsing datetime", e)
        "Undefined"
    }
}