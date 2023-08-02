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
class CurrentConditionsViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    val defaultZipcode: MutableLiveData<String> = MutableLiveData("55076") // sets the default zip before asking for user's input
    val showInvalidZipWarning: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _weatherData: MutableLiveData<CurrentWeatherData> = MutableLiveData()
    val weatherData: LiveData<CurrentWeatherData>
        get() = _weatherData

    fun validateZipAndUpdate(): Boolean { //checks to make sure the zipcode is real
        val currentUserInput = defaultZipcode.value
        if (
            (currentUserInput.isNullOrEmpty() || (currentUserInput.length != 5)) || (currentUserInput.any() { !it.isDigit() })) {
            defaultZipcode.value = "55076"
            return false
        } else {
            Log.d("validateZipAndUpdate()", "valid Zip")
            viewAppeared()
            return true
        }
    }

    fun viewAppeared(zip: String? = defaultZipcode.value) = viewModelScope.launch {
        _weatherData.value = apiService.getWeatherData(zip.toString())
    }

}