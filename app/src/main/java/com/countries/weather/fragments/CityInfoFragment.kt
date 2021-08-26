package com.countries.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.countries.weather.databinding.FragmentCityInfoBinding
import com.countries.weather.viewmodels.CityInfoViewModel
import com.countries.weather.viewmodels.CitySharedViewModel
import kotlinx.android.synthetic.main.fragment_city_info.*

class CityInfoFragment : Fragment() {

    private val locationInfoViewModel: CityInfoViewModel by activityViewModels()
    private val citySharedViewModel: CitySharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCityInfoBinding.inflate(inflater, container, false)

        //WOEID using here to get detail of selected city
        citySharedViewModel.getCity()
            .observe(viewLifecycleOwner, Observer { wcCityID ->
                locationInfoViewModel.getCountryDetails(wcCityID)
            })

        // Mutable LiveData Observing the weather information

        locationInfoViewModel.getCountryInfo()
            .observe(viewLifecycleOwner, Observer { mwLocationInfo ->
                binding.apply {
                    binding.wcLocationInfo = mwLocationInfo
                    binding.forecastSize = mwLocationInfo.consolidated_weather.size
                    executePendingBindings()
                }
            })

        // Mutable LiveData Observing the progress Status

        locationInfoViewModel.getProgressBar().observe(viewLifecycleOwner, Observer {
            progress_bar.visibility = when {
                it -> {
                    View.VISIBLE
                }
                else -> {
                    View.INVISIBLE
                }
            }
        })

        return binding.root
    }

}