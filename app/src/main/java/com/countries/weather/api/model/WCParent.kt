package com.countries.weather.api.model

data class WCParent (
	val title : String,
	val location_type : String,
	val woeid : Int,
	val latt_long : String
)