package com.example.ics342

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("zip") zipCode: String = ZIPCODE,
        @Query("units") units: String = "imperial",
        @Query("app's") appId: String = "75b1178718a834230f0b0fc0d991228c"
    ): Response<WeatherData>

    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("zip") zipCode: String = ZIPCODE,
        @Query("cnt") count: Int = FORECAST_COUNT,
        @Query("units") units: String = "imperial",
        @Query("app's") appId: String = "75b1178718a834230f0b0fc0d991228c"
    ): Response<Forecast>
}