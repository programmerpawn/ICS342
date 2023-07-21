package com.example.ics342

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ics342.ui.theme.ICS342Theme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

private val ForecastItems = listOf(
    DayForecastOld(1, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(2, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(3, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(4, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(5, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(6, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(7, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(8, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(9, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(10, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(11, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(12, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(13, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(14, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(15, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
    DayForecastOld(16, 8, 9, ForecastTemp(1.0F, 1.0F, 1.0F), 1.0F, 1),
)

@Composable
fun DataItemView(ForecastItems: DayForecastOld) {
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
            text = stringResource(id = R.string.month_name) + " " + ForecastItems.date.toString(),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.size(15.dp))

        Column {
            Text(
                text = stringResource(id = R.string.temp_name) + stringResource(id = R.string.currentTempData),
                fontSize = 12.sp
            )
            Row {
                Text(
                    text = stringResource(id = R.string.dailyHigh) + "  ",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
                Text(
                    text = stringResource(id = R.string.dailyLow),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        Column {
            Text(
                text = stringResource(id = R.string.sunrise_name) + ForecastItems.sunrise.toString() + stringResource(
                    id = R.string.am_name
                ),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp
            )
            Text(
                text = stringResource(id = R.string.sunset_name) + ForecastItems.sunset.toString() + stringResource(
                    id = R.string.pm_name
                ),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
fun TempDetailScreen(ForecastItems: List<DayForecastOld>) {

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

    LazyColumn {
        items(ForecastItems) { DayForecast ->
            DataItemView(DayForecast)
        }
    }
}

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
    TempDetailScreen(ForecastItems = ForecastItems)

}


@Preview(showBackground = true)
@Composable
fun ForecastItems() {
    ICS342Theme {
        TempDetailScreen(ForecastItems = ForecastItems)
        val navController = rememberNavController()
        ForecastScreen(navController)
    }
}