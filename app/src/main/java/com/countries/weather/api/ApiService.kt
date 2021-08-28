package com.countries.weather.api

import com.countries.weather.api.model.WCLocation
import com.countries.weather.api.model.WCLocationInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//API callback using coroutine for bg thread

interface ApiService {

    @GET("location/search")
    suspend fun searchLocation(@Query("query") searchLocation: String): Response<MutableList<WCLocation?>>

    @GET("location/{woeid}")
    suspend fun getLocationInfo(@Path("woeid") woeid: Int): Response<WCLocationInfo>

}
