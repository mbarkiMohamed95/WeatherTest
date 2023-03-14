package com.example.weathersettest.presentation.search.action

sealed class SearchViewActions{
    data class LoadSearchedCityWeather(val cityName:String=""): SearchViewActions()
    object SavePlace: SearchViewActions()

}
