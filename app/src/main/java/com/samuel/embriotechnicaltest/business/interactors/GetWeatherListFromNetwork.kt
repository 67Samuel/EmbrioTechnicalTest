package com.samuel.embriotechnicaltest.business.interactors

import android.util.Log
import com.samuel.embriotechnicaltest.business.datasource.cache.WeatherDao
import com.samuel.embriotechnicaltest.business.datasource.cache.WeatherEntity
import com.samuel.embriotechnicaltest.business.datasource.cache.toWeather
import com.samuel.embriotechnicaltest.business.datasource.network.OWMService
import com.samuel.embriotechnicaltest.business.domain.models.Weather
import com.samuel.embriotechnicaltest.business.domain.util.Constants.NETWORK_ERROR
import com.samuel.embriotechnicaltest.business.domain.util.Constants.UNKNOWN_ERROR
import com.samuel.embriotechnicaltest.business.domain.util.DataState
import com.samuel.embriotechnicaltest.business.domain.util.MessageType
import com.samuel.embriotechnicaltest.business.domain.util.Response
import com.samuel.embriotechnicaltest.business.domain.util.UIComponentType
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first

class GetWeatherListFromNetwork(
    private val service: OWMService,
) {
    private val TAG: String = "getWeatherListFromNetworkDebug"

    fun execute(
        apiKey: String,
        lat: String,
        lon: String
    ): Flow<DataState<List<Weather>>> = flow {
        Log.d(TAG, "execute: called")

        val response = service.getWeatherList(apiKey, lat, lon).first()
        Log.d(TAG, "execute: $response")
        if (response.isEmpty()) {
            // network error
            emit(
                DataState.error(
                    response = Response(
                        message = NETWORK_ERROR,
                        uiComponentType = UIComponentType.Toast(),
                        messageType = MessageType.Error()
                    )
                )
            )
        }
        val weatherList = response.map { it.toWeather() }
        emit(
            DataState.data(
                response = null,
                data = weatherList
            )
        )

    }.catch { e ->
        e.printStackTrace()
        Log.e(TAG, "execute: error", e)
//        emit(
//            DataState.error(
//                response = Response(
//                    message = UNKNOWN_ERROR,
//                    uiComponentType = UIComponentType.Dialog(),
//                    messageType = MessageType.Error()
//                )
//            )
//        )
    }
}