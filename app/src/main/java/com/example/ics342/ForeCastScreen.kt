package com.example.ics342

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.ics342.ui.theme.ICS342Theme
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone
import kotlin.math.roundToInt
import kotlin.math.roundToLong


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataItemView(forecast: DayForecast) {
    val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d")
    val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mma")

    Spacer(modifier = Modifier.size(40.dp))
    Row(
        modifier = Modifier
            .padding(8.dp)
    ) {
        WeatherConditionIconDay(url = forecast.iconUrlDay)

        Text(
            text = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(forecast.dayTime),
                TimeZone.getDefault().toZoneId()
            ).format(dateFormat).toString(), //date name
        )

        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "   " + stringResource(id = R.string.temp_name) + " ${forecast.temp.day.roundToInt()}", //gets the current temperature
            )
            Row {
                Text(
                    text = "    High: ${forecast.temp.max.roundToInt()}", //gets the high temperature

                    )
                Text(
                    text = "  Low: ${forecast.temp.min.roundToInt()}", //gets the low temperature

                    )
            }
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sunrise_name) + LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(
                        forecast.sunrise
                    ), TimeZone.getDefault().toZoneId()
                ).format(timeFormat).toString(),
                style = MaterialTheme.typography.bodySmall,

                )

            Text(
                text = stringResource(id = R.string.sunset_name) + LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(
                        forecast.sunset
                    ), TimeZone.getDefault().toZoneId()
                ).format(timeFormat).toString(),
                style = MaterialTheme.typography.bodySmall,

                )
        }
    }
}

@Composable
fun WeatherConditionIconDay( url: String?) {
    AsyncImage(model = url, contentDescription = "",
        modifier =
        Modifier
        .size(55.dp))
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TempDetailScreen(viewModel: ForecastViewModel = hiltViewModel()) {
    //TempDetailScreen(forecast: DayForecast)

    val forecastData = viewModel.forecastData.observeAsState()
    //val forecastList = forecastData.value?.ForecastList //save this for reference

    LaunchedEffect(Unit) {
        viewModel.viewAppeared()
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(id = R.string.forecast_name),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(10.dp)
        )
    }

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
    ) {
        items(items = forecastData.value?.forecastList ?: listOf()) {
            DataItemView(forecast = it)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastScreen(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(5.dp),
            onClick = {
                //Navigates to Home Screen
                navController.navigate("home")
            }
        ) {
            Text(
                text = "Back",
                modifier = Modifier.padding(5.dp),
            )
        }
    }
    TempDetailScreen()
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ForecastItems() {
    ICS342Theme {
        TempDetailScreen()
        val navController = rememberNavController()
        ForecastScreen(navController)
    }
}
