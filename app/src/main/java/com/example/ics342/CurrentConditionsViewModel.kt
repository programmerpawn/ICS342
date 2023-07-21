package com.example.ics342

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ics342.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CurrentConditionsViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    private val _weatherData: MutableLiveData<WeatherData> = MutableLiveData()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData

    fun viewAppeared() {
        viewModelScope.launch {
            try {
                val response = apiService.getWeatherData()
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    _weatherData.value = weatherData
                } else {
                    Log.e("CurrentConditionsViewModel", "API call failed: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("CurrentConditionsViewModel", "Exception: $e")
            }
        }
    }
}