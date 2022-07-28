package com.samuel.embriotechnicaltest.presentation.util

import com.samuel.embriotechnicaltest.R

fun getWeatherImage(mainDesc: String): Int {
    return when(mainDesc) {
        "Thunderstorm" -> R.drawable.thunderstorm
        "Drizzle" -> R.drawable.drizzle
        "Rain" -> R.drawable.rain
        "Snow" -> R.drawable.snow
        "Clear" -> R.drawable.clear
        "Clouds" -> R.drawable.clouds
        else -> R.drawable.clear
    }
}

fun getWeatherIcon(mainDesc: String): Int {
    return when(mainDesc) {
        "Thunderstorm" -> R.drawable.thunderstorm_icon
        "Drizzle" -> R.drawable.drizzle_icon
        "Rain" -> R.drawable.rain_icon
        "Snow" -> R.drawable.snow_icon
        "Clear" -> R.drawable.clear_icon
        "Clouds" -> R.drawable.clouds_icon
        else -> R.drawable.clear_icon
    }
}