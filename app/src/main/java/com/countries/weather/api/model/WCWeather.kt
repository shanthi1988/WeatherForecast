package com.countries.weather.api.model

import com.countries.weather.api.ApiClient

data class WCWeather(
    val id: Long,
    val weather_state_name: String,
    val weather_state_abbr: String,
    val wind_direction_compass: String,
    val created: String,
    val min_temp: Double,
    val max_temp: Double,
    val the_temp: Double,
    val wind_speed: Double,
    val wind_direction: Double,
    val air_pressure: Double,
    val humidity: Int,
    val visibility: Double,
    val predictability: Int
) {
    fun getImageUrl(): String {
        return ApiClient.getImageUrl(weather_state_abbr)
    }

}