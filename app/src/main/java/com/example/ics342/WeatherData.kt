package com.example.ics342

import com.squareup.moshi.Json

const val ZIPCODE = "55076"
const val FORECAST_COUNT = 16


data class WeatherData(
    @Json(name = "conditionDescription") val conditionDescription: ConditionDescription,
    @Json(name = "name") val name: String,
    @Json(name = "weather") private val weatherConditions: List<WeatherConditions>,
) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weatherConditions.firstOrNull()?.icon}@2x.png"
}

data class Forecast(
    @Json(name = "list") val forecasts: List<DayForecastOld>,
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


data class ConditionDescription(
    @Json(name = "temp") val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
)


data class ForecastTempNew(
    @Json(name = "day") val day: Float,
    @Json(name = "min") val min: Float,
    @Json(name = "max") val max: Float,
)

data class WeatherConditions(
    @Json(name = "icon") val icon: String,
)