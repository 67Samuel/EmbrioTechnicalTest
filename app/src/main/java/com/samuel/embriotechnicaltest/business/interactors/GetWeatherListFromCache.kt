package com.samuel.embriotechnicaltest.business.interactors

import android.util.Log
import com.samuel.embriotechnicaltest.business.datasource.cache.WeatherDao
import com.samuel.embriotechnicaltest.business.datasource.cache.toWeather
import com.samuel.embriotechnicaltest.business.domain.models.Weather
import com.samuel.embriotechnicaltest.business.domain.util.*
import com.samuel.embriotechnicaltest.business.domain.util.Constants.CACHE_ERROR
import com.samuel.embriotechnicaltest.business.domain.util.DataState
import com.samuel.embriotechnicaltest.business.domain.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWeatherListFromCache(
    private val cache: WeatherDao
) {
    private val TAG: String = "GetWeatherListFromCacheDebug"

    fun execute(): Flow<DataState<List<Weather>>> = flow {
        Log.d(TAG, "execute: called")

        val weatherList = cache.getWeatherList().map { it.toWeather() }

        if (weatherList.isNotEmpty()) {
            emit(DataState.data(
                data = weatherList,
                response = null
            ))
        } else {
            emit(DataState.error(
                response = Response(
                    message = CACHE_ERROR,
                    uiComponentType = UIComponentType.Toast(),
                    messageType = MessageType.Error()
                )
            ))
        }
    }
}