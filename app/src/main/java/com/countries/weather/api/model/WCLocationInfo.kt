package com.countries.weather.api.model

data class WCLocationInfo(
    val consolidated_weather: List<WCWeather>,
    val parent: WCParent,
    val title: String,
    val woeid: Int
)
