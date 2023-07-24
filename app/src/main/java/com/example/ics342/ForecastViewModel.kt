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
    private val _forecastLiveData: MutableLiveData<List<DayForecastNew>> = MutableLiveData()
    val forecastData: LiveData<List<DayForecastNew>>
        get() = _forecastLiveData

    fun fetchWeatherForecast() {
        viewModelScope.launch {
            try {
                val response = apiService.getForecast()
                if (response.isSuccessful) {
                    val forecastData = response.body()
                    if (forecastData != null) {
                        _forecastLiveData.value = forecastData.forecasts
                    } else {
                        Log.e("ForecastViewModel", "empty list")
                    }
                } else {
                    Log.e("ForecastViewModel", "API call failed: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("ForecastViewModel", "Exception: $e")
            }
        }
    }
}