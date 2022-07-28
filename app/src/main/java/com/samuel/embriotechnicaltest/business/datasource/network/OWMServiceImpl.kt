package com.samuel.embriotechnicaltest.business.datasource.network

import android.util.Log
import com.samuel.embriotechnicaltest.business.datasource.cache.WeatherEntity
import com.samuel.embriotechnicaltest.business.datasource.cache.fromOWMResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class OWMServiceImpl : OWMService {
    private val TAG: String = "OWMServiceImplDebug"

    override suspend fun getWeatherList(
        apiKey: String,
        lat: String,
        lon: String,
    ): Flow<List<WeatherEntity>> {
        Log.d(TAG, "getWeatherList: called")
        return flow {
            val reader: BufferedReader
            val url =
                URL("https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&appid=$apiKey")

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"
                reader = BufferedReader(InputStreamReader(inputStream) as Reader?)

                val response = StringBuffer()
                var inputLine = reader.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = reader.readLine()
                }
                reader.close()

                val weatherEntityList = mutableListOf<WeatherEntity>()
                if (responseCode in 200..299) {
                    for (i in 0..39 step 8) { // response contains 8 reports per day, but for now we only want to use one
                        weatherEntityList.add(fromOWMResponse(response, i))
                    }
                    Log.d(TAG, "getWeatherList: weatherEntityList: $weatherEntityList")
                } else {
                    Log.e(TAG,
                        "getWeatherList: response code: $responseCode\nresponse message: $responseMessage")
                }
                emit(weatherEntityList.toList()) // if error, return empty list
            }
        }.flowOn(Dispatchers.IO)
    }
}