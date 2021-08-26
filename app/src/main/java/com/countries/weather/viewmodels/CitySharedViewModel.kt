package com.countries.weather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CitySharedViewModel : ViewModel() {

    private val wcCityID = MutableLiveData<Int>()

    fun getCity(): LiveData<Int> {
        return wcCityID
    }

    fun selectedCity(cityWoeid: Int) {
        this.wcCityID.postValue(cityWoeid);
    }

}