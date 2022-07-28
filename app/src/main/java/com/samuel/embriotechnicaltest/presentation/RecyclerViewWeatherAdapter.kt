package com.samuel.embriotechnicaltest.presentation

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.samuel.embriotechnicaltest.R
import com.samuel.embriotechnicaltest.business.domain.models.Weather
import com.samuel.embriotechnicaltest.presentation.util.getWeatherIcon
import com.samuel.embriotechnicaltest.presentation.util.getWeatherImage

/**
 * - List of objects are kept in the AsyncListDiffer differ, referenced by differ.currentList
 */

class RecyclerViewWeatherAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Weather>() {

        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.day == newItem.day
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.day == newItem.day
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_report,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WeatherViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // for submitting new list items to the current list
    fun submitList(list: List<Weather>) {
        differ.submitList(list)
    }

    class WeatherViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?,
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Weather) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            val day = itemView.findViewById<TextView>(R.id.day)
            day.text = item.day
            day.setCompoundDrawablesWithIntrinsicBounds(0,
                getWeatherIcon(item.main_desc),
                0,
                0)
        }
    }

    // interface for detecting clicks
    // usage: pass in an object that extends Interaction when initializing the adapter
    interface Interaction {
        fun onItemSelected(position: Int, item: Weather)
    }
}