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

    val defaultZipcode: MutableLiveData<String> = MutableLiveData("55076")
    private val _forecastLiveData: MutableLiveData<ForecastData> = MutableLiveData()
    val forecastData: LiveData<ForecastData>
        get() = _forecastLiveData

    fun validateZip(zip : String?): Boolean {
        if (
            (zip.isNullOrEmpty() || zip.length != 5) || (zip.any() { !it.isDigit() })) {
            return false
        } else {
            Log.d("forecastVM.validateZip()", "valid Zip")
            defaultZipcode.value = zip
            viewAppeared()
            return true
        }
    }

    fun viewAppeared(zip: String? = defaultZipcode.value) = viewModelScope.launch {
        _forecastLiveData.value = apiService.getForecastData(zip.toString())
    }
}