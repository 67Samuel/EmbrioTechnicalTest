package com.samuel.embriotechnicaltest.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samuel.embriotechnicaltest.business.domain.util.StateMessage
import com.samuel.embriotechnicaltest.business.domain.util.doesMessageAlreadyExistInQueue
import com.samuel.embriotechnicaltest.business.interactors.GetWeatherListFromNetwork
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    private val getWeatherListFromNetwork: GetWeatherListFromNetwork,
) : ViewModel() {

    private val TAG: String = "MainViewModelDebug"

    private val _state: MutableLiveData<MainState> = MutableLiveData(MainState())
    val state: LiveData<MainState> = _state

    init {
        onTriggerMainEvent(MainEvents.GetWeatherListFromNetwork("16671418d758526c63ca6600f06a2951", "1.3521", "103.8198"))
    }

    fun onTriggerMainEvent(event: MainEvents) {
        when(event) {
            is MainEvents.GetWeatherListFromNetwork -> {
                getWeatherListFromNetwork(event.apiKey, event.lat, event.lon)
            }
            is MainEvents.Error -> {
                appendToMessageQueue(event.stateMessage)
            }
            is MainEvents.OnRemoveHeadFromQueue -> {
                removeHeadFromQueue()
            }
        }
    }

    private fun getWeatherListFromNetwork(
        apiKey: String,
        lat: String,
        lon: String
    ) {
        Log.d(TAG, "getWeatherListFromNetwork: called")
        state.value?.let {
            CoroutineScope(Dispatchers.IO).launch {
                getWeatherListFromNetwork.execute(
                    apiKey,
                    lat,
                    lon
                ).onEach { dataState ->
                    Log.d(TAG, "getWeatherListFromNetwork: dataState: $dataState")
                    withContext(Dispatchers.Main) {
                        dataState.data?.let { weatherList ->
                            Log.d(TAG, "getWeatherListFromNetwork: weatherList: $weatherList")
                            _state.value = state.value!!.copy(weatherList = weatherList)
                        }

                        dataState.stateMessage?.let { stateMessage ->
                            appendToMessageQueue(stateMessage)
                        }
                    }
                }.collect { }
            }
        }
    }


    private fun appendToMessageQueue(stateMessage: StateMessage){
        Log.d(TAG, "appendToMessageQueue: called")
        state.value?.let {
            val queue = state.value!!.queue
            if(!stateMessage.doesMessageAlreadyExistInQueue(queue = queue)){
                queue.add(stateMessage)
                _state.value = state.value!!.copy(queue = queue)
            }
        }
    }

    private fun removeHeadFromQueue() {
        Log.d(TAG, "removeHeadFromQueue: called")
        state.value?.let {
            try {
                val queue = state.value!!.queue
                queue.remove() // can throw exception if empty
                _state.value = state.value!!.copy(queue = queue)
            } catch (e: Exception) {
                Log.d(TAG, "removeHeadFromQueue: Nothing to remove from DialogQueue")
            }
        }
    }



    class Factory(private val getWeatherListFromNetwork: GetWeatherListFromNetwork): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getWeatherListFromNetwork) as T
        }
    }
}

