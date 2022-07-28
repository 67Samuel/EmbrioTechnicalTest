package com.samuel.embriotechnicaltest.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.samuel.embriotechnicaltest.business.datasource.network.OWMServiceImpl
import com.samuel.embriotechnicaltest.business.domain.models.Weather
import com.samuel.embriotechnicaltest.business.domain.util.StateMessageCallback
import com.samuel.embriotechnicaltest.business.interactors.GetWeatherListFromNetwork
import com.samuel.embriotechnicaltest.databinding.ActivityMainBinding
import com.samuel.embriotechnicaltest.presentation.util.getWeatherIcon
import com.samuel.embriotechnicaltest.presentation.util.getWeatherImage
import com.samuel.embriotechnicaltest.presentation.util.processQueue

class MainActivity : AppCompatActivity(), RecyclerViewWeatherAdapter.Interaction {

    private val TAG: String = "MainActivityDebug"

    private lateinit var binding: ActivityMainBinding
    private var recyclerAdapter: RecyclerViewWeatherAdapter? = null

    private val service: OWMServiceImpl = OWMServiceImpl()
    private val getWeatherListFromNetwork: GetWeatherListFromNetwork = GetWeatherListFromNetwork(service)
    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory(getWeatherListFromNetwork) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.state.observe(this) { state ->
            Log.d(TAG, "subscribeObservers: $state")

            recyclerAdapter?.apply {
                submitList(list = state.weatherList)
            }

            state.weatherList.firstOrNull()?.let { firstWeather ->
                binding.apply {
                    day.text = firstWeather.day
                    day.setCompoundDrawablesWithIntrinsicBounds(0,
                        0,
                        getWeatherIcon(firstWeather.main_desc),
                        0)
                    desc.text = firstWeather.desc
                    feelsLike.text = "Feels Like: ${firstWeather.feelsLike}ºC"
                    minTemp.text = "Min Temp: ${firstWeather.minTemp}ºC"
                    maxTemp.text = "Max Temp: ${firstWeather.maxTemp}ºC"
                    humidity.text = "Humidity: ${firstWeather.humidity}%"
                    backgroundLayout.background = AppCompatResources.getDrawable(this@MainActivity, getWeatherImage(firstWeather.main_desc))
                }
            }

            processQueue(
                context = this@MainActivity,
                queue = state.queue,
                stateMessageCallback = object : StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.onTriggerMainEvent(MainEvents.OnRemoveHeadFromQueue)
                    }
                })
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            recyclerAdapter = RecyclerViewWeatherAdapter(this@MainActivity)
            adapter = recyclerAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Weather) {
        binding.apply {
            day.text = item.day
            day.setCompoundDrawablesWithIntrinsicBounds(0, 0,  getWeatherIcon(item.main_desc),0)
            desc.text = item.desc
            feelsLike.text = "Feels Like: ${item.feelsLike}ºC"
            minTemp.text = "Min Temp: ${item.minTemp}ºC"
            maxTemp.text = "Max Temp: ${item.maxTemp}ºC"
            humidity.text = "Humidity: ${item.humidity}%"
            backgroundLayout.background = AppCompatResources.getDrawable(this@MainActivity, getWeatherImage(item.main_desc))
        }
    }
}