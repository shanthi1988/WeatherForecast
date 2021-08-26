package com.countries.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.countries.weather.api.model.WCLocation
import com.countries.weather.databinding.ViewCityBinding

class CityAdapter(private val listener: InteractionListener) :
    ListAdapter<WCLocation, RecyclerView.ViewHolder>(CityDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CityViewHolder(
            listener,
            ViewCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CityViewHolder).bind(getItem(position))
    }

    class CityViewHolder(
        private val listener: InteractionListener,
        private val binding: ViewCityBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.wcLocation?.let { mwLocation ->
                    listener.onItemSelected(mwLocation.woeid)
                }
            }
        }

        fun bind(item: WCLocation) {
            binding.apply {
                wcLocation = item
                executePendingBindings()
            }
        }
    }

    private class CityDiffCallBack : DiffUtil.ItemCallback<WCLocation>() {
        override fun areItemsTheSame(oldItem: WCLocation, newItem: WCLocation): Boolean {
            return oldItem.woeid == newItem.woeid
        }

        override fun areContentsTheSame(oldItem: WCLocation, newItem: WCLocation): Boolean {
            return oldItem == newItem
        }
    }

    interface InteractionListener {
        fun onItemSelected(mwLocationID: Int)
    }
}