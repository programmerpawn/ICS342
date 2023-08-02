package com.example.ics342

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("zip") zipCode: String,
        @Query("units") units: String = "imperial",
        @Query("appId") appId: String = "75b1178718a834230f0b0fc0d991228c"
    ): CurrentWeatherData

    @GET("forecast/daily")
    suspend fun getForecastData(
        @Query("zip") zipCode: String,
        @Query("cnt") count: Int = 16,
        @Query("units") units: String = "imperial",
        @Query("appId") appId: String = "75b1178718a834230f0b0fc0d991228c"
    ): ForecastData
}