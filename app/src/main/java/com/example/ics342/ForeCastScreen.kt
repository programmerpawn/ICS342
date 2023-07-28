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
import com.example.ics342.ui.theme.ICS342Theme
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataItemView(forecast: DayForecast) {
    val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d")
    val timeFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mma")

    Spacer(modifier = Modifier.size(40.dp))
    Row {
        val imageMod = Modifier
            .size(25.dp)
        Image(
            painter = painterResource(id = R.drawable.sunny),
            contentDescription = "sun",
            contentScale = ContentScale.FillBounds,
            modifier = imageMod
        )

        Text(
            text = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(forecast.dayTime),
                TimeZone.getDefault().toZoneId()
            ).format(dateFormat).toString(), //date name
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.size(15.dp))

        Column {
            Text(
                text = stringResource(id = R.string.temp_name) + "${forecast.temp}", //gets the current temperature
                fontSize = 12.sp
            )
            Row {
                Text(
                    text = "${forecast.temp.max}", //gets the high temperature
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
                Text(
                    text = "${forecast.temp.min}", //gets the low temperature
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        Column {
            Text(
                text = stringResource(id = R.string.sunrise_name) + LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(
                        forecast.sunrise
                    ), TimeZone.getDefault().toZoneId()
                ).format(timeFormat).toString() + stringResource(
                    id = R.string.am_name
                ),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp
            )

            Text(
                text = stringResource(id = R.string.sunset_name) + LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(
                        forecast.sunset
                    ), TimeZone.getDefault().toZoneId()
                ).format(timeFormat).toString() + stringResource(
                    id = R.string.pm_name
                ),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TempDetailScreen(viewModel: ForecastViewModel = hiltViewModel()) {
    //TempDetailScreen(forecast: DayForecast)

    val forecastData = viewModel.forecastData.observeAsState()
    //val forecastList = forecastData.value?.ForecastList //save this for reference

    LaunchedEffect(Unit) {
        viewModel.fetchWeatherForecast()
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
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
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
        TempDetailScreen() //ideas on how to call this. call it or merge it with forecastlist
    }
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

/*@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastList(viewModel: ForecastViewModel = hiltViewModel()) { //merge this with tempdetailscreen or call this with tempdetailscreen or call tempdetailscree here


    val forecastData = viewModel.forecastData.observeAsState()
    //val forecastList = forecastData.value?.ForecastList save this for reference

    LaunchedEffect(Unit) {
        viewModel.fetchWeatherForecast()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        items(items = forecastData.value?.ForecastList ?: listOf()) {
            DataItemView(forecast = it)
        }
    }
}
*/

/*
fun OldForecastView(){
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(2.dp)) {
                    Text(
                        text = "${forecastData.}", // Display the formatted date
                        fontWeight = FontWeight.Bold,
                    )
                }
                Column {
                    Text(
                        text = "Temp: ${forecast.dayTemp}°", // Display the day temperature
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = "High: ${forecast.highTemp}°", // Display the maximum temperature
                    )
                }
                Text(
                    text = "Low: ${forecast.lowTemp}°",  // Display the minimum temperature
                    modifier = Modifier.padding(2.dp, 28.dp, 1.dp)
                )
                Column {
                    Text(
                        text = "Sunrise: ${forecast.sunrise}", // Display the sunrise time
                    )
                    Spacer(modifier = Modifier.size(6.dp))
                    Text(
                        text = "Sunset: ${forecast.sunset}", // Display the sunset time
                    )
                }
            }
        }
    }
}
*/