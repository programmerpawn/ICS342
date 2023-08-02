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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ics342.ui.theme.ICS342Theme
import com.example.ics342.ui.theme.PurpleGrey40
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
    CurrentZipCode(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentZipCode(viewModel: CurrentConditionsViewModel) {
    val userInput = viewModel.defaultZipcode.observeAsState()
    val showAlert = viewModel.showInvalidZipWarning.observeAsState(initial = false)
    Column {
        OutlinedTextField(
            value = userInput.value.toString(),
            leadingIcon = {},
            label = {
                Text(
                    text = ("Zip code: "),
                    style = MaterialTheme.typography.headlineMedium,
                    color = PurpleGrey40
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.padding(12.dp),
            onValueChange = { viewModel.defaultZipcode.value = it}
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier)
            Button(onClick = {
                viewModel.showInvalidZipWarning.value = !viewModel.validateZipAndUpdate()

            }) {
                Text(text = "Zip code: ")
            }
            Spacer(modifier = Modifier)
        }
    }
    if (showAlert.value) {
        InvalidZipAlert {
            viewModel.showInvalidZipWarning.value = false
        }
    }
}
@Composable
fun InvalidZipAlert(
    onDismiss: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismiss,
        confirmButton = @Composable {
            Button(onClick = onDismiss) {
                Text("Ok")
            }
        },
        title = @Composable {
            Text(text = "Invalid US ZIP Code")
        },
        text = @Composable {
            Text(text = "You entered an invalid US ZIP code. Please try again.")
        }
    )

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
