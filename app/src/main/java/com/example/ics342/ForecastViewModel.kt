package com.example.ics342

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val _forecastLiveData: MutableLiveData<ForecastData> = MutableLiveData()
    val forecastData: LiveData<ForecastData>
        get() = _forecastLiveData

    fun fetchWeatherForecast() {
            viewModelScope.launch {
                _forecastLiveData.value = apiService.getForecastData()
            }
    }
}