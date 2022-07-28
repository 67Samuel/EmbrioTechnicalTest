package com.samuel.embriotechnicaltest.business.domain.models

import android.util.Log
import com.samuel.embriotechnicaltest.business.datasource.cache.WeatherEntity
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

private val TAG: String = "WeatherDebug"

data class Weather(
    val day: String,
    val temp: String,
    val feelsLike: String,
    val minTemp: String,
    val maxTemp: String,
    val humidity: String,
    val desc: String,
    val main_desc: String
)

fun Weather.toWeatherEntity(i: Int): WeatherEntity {
    return WeatherEntity(
        dayIndex = i,
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
