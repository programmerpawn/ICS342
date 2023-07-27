package com.example.ics342


import com.squareup.moshi.Json

const val ZIPCODE = "55076"
const val FORECAST_COUNT = 16

data class DayForecast(
// creates the day weather data
    @Json(name = "dt") val dayTime: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
    @Json(name = "temp") val temp: ForecastTemp,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "weather") val weather: List<ForeCastConditions>,
) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"
}

data class ForecastTemp(
    @Json(name = "day") val day: Float,
    @Json(name = "min") val min: Float,
    @Json(name = "max") val max: Float,
)

data class ForecastData(
    @Json(name = "list") val ForecastList: List<DayForecast>,
)

data class ForeCastConditions(
    @Json(name = "icon") val icon: String,
)

////////////////////////////////////////
data class CurrentWeather(
    //creates the current weather data list
    @Json(name = "temp") val temp: Float,
    @Json(name = "feels_like") val feelsLike: Float,
    @Json(name = "temp_min") val tempMin: Float,
    @Json(name = "temp_max") val tempMax: Float,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
)

data class CurrentWeatherData(
    @Json(name = "main") val main: CurrentWeather,
    @Json(name = "weather") val weather: List<WeatherConditions>,
) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"
}

data class WeatherConditions(
    @Json(name = "icon") val icon: String,
)