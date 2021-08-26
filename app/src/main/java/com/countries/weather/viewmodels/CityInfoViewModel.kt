package com.countries.weather.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.countries.weather.api.ApiClient
import com.countries.weather.api.model.WCLocationInfo
import kotlinx.coroutines.launch

//Launching the API call within viewModelScope and handled the response

class CityInfoViewModel() : ViewModel() {

    private var locationInfoList: WCLocationInfo? = null
    private val locationInfo = MutableLiveData<WCLocationInfo>()
    private val progressBar = MutableLiveData<Boolean>()
    private var lastWCCityID:Int? = null

    fun getProgressBar(): LiveData<Boolean> {
        return progressBar
    }

    fun getCountryInfo(): LiveData<WCLocationInfo> {
        return locationInfo
    }

    fun getCountryDetails(cityId: Int) {

        if(lastWCCityID != null && lastWCCityID == cityId){
            return
        }
        progressBar.postValue(true)

        viewModelScope.launch {
            try {
                locationInfoList = ApiClient.instance.getLocationInfo(cityId).body()
                locationInfo.postValue(locationInfoList)
                lastWCCityID = cityId
                progressBar.postValue(false)
            } catch (e: Exception) {
                Log.i(TAG,"Something went wrong")
            }

        }
    }

    companion object {
        val TAG = CityInfoViewModel::class.java.simpleName
    }

}