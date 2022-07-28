package com.samuel.embriotechnicaltest.presentation

import com.samuel.embriotechnicaltest.business.domain.models.Weather
import com.samuel.embriotechnicaltest.business.domain.util.Queue
import com.samuel.embriotechnicaltest.business.domain.util.StateMessage

data class MainState(
    val weatherList: List<Weather> = listOf(),
    val queue: Queue<StateMessage> = Queue(mutableListOf()),
)
