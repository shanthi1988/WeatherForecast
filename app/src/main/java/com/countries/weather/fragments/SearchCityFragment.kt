package com.countries.weather.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.countries.weather.R
import com.countries.weather.adapter.CityAdapter
import com.countries.weather.databinding.FragmentSearchCityBinding
import com.countries.weather.viewmodels.CitySearchViewModel
import com.countries.weather.viewmodels.CitySharedViewModel
import kotlinx.android.synthetic.main.fragment_search_city.*


class SearchCityFragment : Fragment() {

    private val citySearchViewModel: CitySearchViewModel by activityViewModels()
    private val citySharedViewModel: CitySharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSearchCityBinding.inflate(inflater, container, false)

        val adapter =
            CityAdapter(object :
                CityAdapter.InteractionListener {
                override fun onItemSelected(wcCityId: Int) {
                    citySharedViewModel.selectedCity(wcCityId)
                    findNavController().navigate(R.id.action_searchCityFragment_to_cityInfoFragment)
                }
            });

        binding.recentSearchList.adapter = adapter

        //geCities-Mutable LiveData will return the list of cities based on user search

        citySearchViewModel.getCities().observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
        })

        //getStatus- Mutable LiveData will return boolean if any error occurred

        citySearchViewModel.getStatus().observe(viewLifecycleOwner, Observer {
            txt_no_data.visibility = when {
                it -> {
                    View.VISIBLE
                }else -> {
                    View.INVISIBLE
                }
            }
        })

        //getStatus- Mutable LiveData will return boolean to handle progressbar

        citySearchViewModel.getProgressBar().observe(viewLifecycleOwner, Observer {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_box.doAfterTextChanged { city: Editable? ->
            citySearchViewModel.searchCountry(city.toString())
        }
    }
}
