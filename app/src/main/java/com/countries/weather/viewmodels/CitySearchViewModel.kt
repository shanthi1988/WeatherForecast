package com.countries.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.countries.weather.api.ApiClient
import com.countries.weather.api.model.WCLocation
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//Launching the API call within viewModelScope and handled the response

class CitySearchViewModel : ViewModel() {

    private var searchCityList: MutableList<WCLocation?>? = null
    private var searchDefaultList: MutableList<WCLocation?>? = null

    private val locations = MutableLiveData<List<WCLocation?>>()
    private val defaultLocation = MutableLiveData<List<WCLocation?>>()

    private val progressBar = MutableLiveData<Boolean>()
    private val noData = MutableLiveData<Boolean>()

    private var searchString:String? = null

    fun getProgressBar(): LiveData<Boolean> {
        return progressBar
    }
    fun getStatus(): LiveData<Boolean> {
        return noData
    }

    fun getCities(): LiveData<List<WCLocation?>> {
        return locations
    }

    fun getDefaultLocation():LiveData<List<WCLocation?>>{
        return defaultLocation
    }
    init {
        fetchDefaultCountry()
    }

    //Default Countries list : parallel task done using coroutine

    fun fetchDefaultCountry(){
        if (searchDefaultList.isNullOrEmpty()){
            progressBar.postValue(true)
            viewModelScope.launch {
                try {
                    val allUsersFromApi = mutableListOf<WCLocation?>()

                    val cityGothenburg = async { ApiClient.instance.searchLocation("Gothenburg") }
                    val cityStockHolm = async { ApiClient.instance.searchLocation("Stockholm") }
                    val cityMountain = async { ApiClient.instance.searchLocation("Mountain View") }
                    val cityLondon = async { ApiClient.instance.searchLocation("London") }
                    val cityNewYork = async { ApiClient.instance.searchLocation("New York") }
                    val cityBerlin = async { ApiClient.instance.searchLocation("Berlin") }

                    val detailsCityGothenburg = cityGothenburg.await()
                    allUsersFromApi.add(detailsCityGothenburg?.body()?.get(0))

                    val detailsCityStockHolm = cityStockHolm.await()
                    allUsersFromApi.add(detailsCityStockHolm?.body()?.get(0))

                    val detailsCityMountain = cityMountain.await()
                    allUsersFromApi.add(detailsCityMountain?.body()?.get(0))

                    val detailsCityNewyork = cityNewYork.await()
                    allUsersFromApi.add(detailsCityNewyork?.body()?.get(0))

                    val detailsCitBerlin = cityBerlin.await()
                    allUsersFromApi.add(detailsCitBerlin?.body()?.get(0))

                    val detailsCityLondon = cityLondon.await()
                    allUsersFromApi.add(detailsCityLondon?.body()?.get(0))

                    searchCityList = allUsersFromApi
                    searchDefaultList = allUsersFromApi
                    defaultLocation.postValue(allUsersFromApi)
                    locations.postValue(searchCityList)
                    progressBar.postValue(false)
                    noData.postValue(false)

                } catch (e: Exception) {
                    noData.postValue(true)
                }
            }
        }else {
            defaultLocation.postValue(searchDefaultList)
        }

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