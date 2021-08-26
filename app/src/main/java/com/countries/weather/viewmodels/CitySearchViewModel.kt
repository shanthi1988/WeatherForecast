package com.countries.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.countries.weather.api.ApiClient
import com.countries.weather.api.model.WCLocation
import kotlinx.coroutines.launch

//Launching the API call within viewModelScope and handled the response

class CitySearchViewModel : ViewModel() {

    private var searchCityList: List<WCLocation>? = null
    private val locations = MutableLiveData<List<WCLocation>>()
    private val progressBar = MutableLiveData<Boolean>()
    private val noData = MutableLiveData<Boolean>()

    private var searchString:String? = null

    fun getProgressBar(): LiveData<Boolean> {
        return progressBar
    }
    fun getStatus(): LiveData<Boolean> {
        return noData
    }

    fun getCities(): LiveData<List<WCLocation>> {
        return locations
    }

    fun searchCountry(searchLocation: String) {
        if(searchString != null && searchString == searchLocation){
           return
        }
        progressBar.postValue(true)
         viewModelScope.launch {
             try {
                 searchCityList = ApiClient.instance.searchLocation(searchLocation).body()
                 locations.postValue(searchCityList)
                 progressBar.postValue(false)
                 noData.postValue(false)
             } catch (e: Exception) {
                 noData.postValue(true)
             }
         }
    }

}