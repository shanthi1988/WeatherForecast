package com.countries.weather.api

import com.countries.weather.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface ApiClient {

    companion object {

        // Singleton instance ,using lazy to avoid unnecessary object creation before use.
        val instance: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.DOMAIN + "api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(ApiService::class.java)
        }

        fun getImageUrl(abbr: String): String {
            return BuildConfig.DOMAIN + "static/img/weather/png/" + abbr + ".png"
        }
    }


}