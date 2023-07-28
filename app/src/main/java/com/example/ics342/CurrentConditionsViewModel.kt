package com.example.ics342

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrentConditionsViewModel @Inject constructor(private val apiService: ApiService) :
    ViewModel() {

    private val _weatherData: MutableLiveData<CurrentWeatherData> = MutableLiveData()
    val weatherData: LiveData<CurrentWeatherData>
        get() = _weatherData

    fun viewAppeared() {
        viewModelScope.launch {
            _weatherData.value = apiService.getWeatherData()
        }
    }
}