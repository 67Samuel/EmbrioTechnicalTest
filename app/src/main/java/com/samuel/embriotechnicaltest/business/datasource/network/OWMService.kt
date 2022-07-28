package com.samuel.embriotechnicaltest.business.datasource.network

import com.samuel.embriotechnicaltest.business.datasource.cache.WeatherEntity
import com.samuel.embriotechnicaltest.business.domain.models.Weather
import kotlinx.coroutines.flow.Flow

interface OWMService {

    suspend fun getWeatherList(
        apiKey: String,
        lat: String,
        lon: String
    ): Flow<List<WeatherEntity>>
}