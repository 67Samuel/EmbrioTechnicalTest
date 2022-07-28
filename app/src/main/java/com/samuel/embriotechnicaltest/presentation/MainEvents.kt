package com.samuel.embriotechnicaltest.presentation

import com.samuel.embriotechnicaltest.business.domain.util.StateMessage

sealed class MainEvents {

    data class GetWeatherListFromNetwork(
        val apiKey: String,
        val lat: String,
        val lon: String
    ) : MainEvents()

    object OnRemoveHeadFromQueue : MainEvents()
    data class Error(val stateMessage: StateMessage): MainEvents()
}
