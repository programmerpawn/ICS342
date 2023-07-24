package com.example.ics342

import com.squareup.moshi.Json

const val ZIPCODE = "55076"
const val FORECAST_COUNT = 16

data class CurrentConditions(
    @Json(name = "weather") private val weatherSummaryList : List<WeatherSummary>,
    @Json(name = "main") private val weatherData : WeatherData,
    @Json(name = "name") val location : String
) {
    val weatherIconUrl : String
        get() = "https://openweathermap.org/img/wn/${weatherSummaryList.firstOrNull()?.icon}@2x.png"
    val weatherDescription : String?
        get() = weatherSummaryList.firstOrNull()?.description
    val currentTemp : Float
        get() = weatherData.currentTemp
    val feelsLike : Float
        get() = weatherData.feelsLike
    val pressure : Int
        get() = weatherData.pressure
    val humidity : Int
        get() = weatherData.humidity
    val maxTemp : Float
        get() = weatherData.maxTemp
    val minTemp : Float
        get() = weatherData.minTemp
}

data class WeatherSummary(
    val description : String,
    val icon : String

)

data class WeatherData(
    @Json(name = "temp") val currentTemp : Float,
    @Json(name = "feels_like") val feelsLike : Float,
    @Json(name = "temp_min") val minTemp : Float,
    @Json(name = "temp_max") val maxTemp : Float,
    val pressure : Int,
    val humidity : Int
)
data class Forecast(
    @Json(name = "list") val forecasts: List<DayForecastNew>,
)

data class DayForecastNew(
    @Json(name = "dt") val dayTime: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
    @Json(name = "temp") val temp: ForecastTempNew,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "weather") private val weatherConditions: List<WeatherConditions>,
) {
    @Json(name = "day")
    val dayTemp: Float
        get() = temp.day

    @Json(name = "min")
    val lowTemp: Float
        get() = temp.min

    @Json(name = "max")
    val highTemp: Float
        get() = temp.max

    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
}
data class ForecastTempNew(
    @Json(name = "day") val day: Float,
    @Json(name = "min") val min: Float,
    @Json(name = "max") val max: Float,
)
data class WeatherConditions(
    @Json(name = "icon") val icon: String,
)