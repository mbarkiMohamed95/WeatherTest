package com.example.weathersettest.presentation.home.action

sealed class HomeAction(){
    object LoadWeather:HomeAction()
    object UpdateWeather:HomeAction()
}
