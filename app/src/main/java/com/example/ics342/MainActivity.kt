package com.example.ics342

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ics342.ui.theme.ICS342Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToLong

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ICS342Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {}
            }
            NavigationView()
        }
    }
}


@Composable
fun WeatherView(viewModel: CurrentConditionsViewModel = hiltViewModel()) { //ADD IN THE TITLES FOR EACH OF THE TEMPS HERE THEN IT'S DONE

    val weatherData = viewModel.weatherData.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.viewAppeared()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name), //title of the app
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(10.dp)
        )
        Text(
            text = stringResource(id = R.string.myLocation),//Sets the location where the temperature is located
            fontSize = 15.sp,
            modifier = Modifier.padding(10.dp)
        )
        Row {
            Column(modifier = Modifier.padding(5.dp)) {
                Text(
                    text = "${weatherData.value?.main?.temp?.roundToLong()}" + stringResource(id = R.string.temp_logo), //Sets the current Temp
                    fontSize = 45.sp
                )
                Text(
                    text = "Feels like ${weatherData.value?.main?.feelsLike?.roundToLong()}º", //Sets the "feels like" temp
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(25.dp))

                //other information about the weather
                Text(
                    text = "Low ${weatherData.value?.main?.tempMin?.roundToLong()}º" //daily low
                )
                Text(
                    text = "High ${weatherData.value?.main?.tempMax?.roundToLong()}º" //daily high
                )
                Text(
                    text = "Humidity ${weatherData.value?.main?.humidity}%" //humidity
                )
                Text(
                    text = "Pressure ${weatherData.value?.main?.pressure} hPa" //pressure
                )

            }

            Column(modifier = Modifier.padding(5.dp)) { //Sets the picture
                val imageMod = Modifier
                    .size(75.dp)
                Image(
                    painter = painterResource(id = R.drawable.sunny),
                    contentDescription = "sun",
                    contentScale = ContentScale.FillBounds,
                    modifier = imageMod
                )
            }
        }
    }
}



@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(Screens.ForecastScreen.route) },
            colors = ButtonDefaults.buttonColors(Color.Gray.copy(alpha = 1F)),
        ) { Text(text = "Forecast") }
    }
        WeatherView()
}


//Navigation function: home screen and detail screen
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationView() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)

        }
        composable(Screens.ForecastScreen.route) {
            ForecastScreen(navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ICS342Theme {
        NavigationView()
    }
}
