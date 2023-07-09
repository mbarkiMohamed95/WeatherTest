package com.example.weathersettest.presentation.detail.compose

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.example.domain.detail.model.DetailWeatherUiModel
import com.example.weathersettest.tools.ui.convertTimestamp
import com.example.weathersettest.tools.ui.loadImage

@Composable
fun HourlyWeatherDisplay(
    weatherData: DetailWeatherUiModel,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    context:Context
) {
    val formattedTime = remember(weatherData) {
        convertTimestamp(weatherData.lastUpdate)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            color = Color.LightGray
        )
        loadImage("${weatherData.icon}.png",context)?.let {
            Image(
                bitmap = it
                    .toBitmap(150,150).asImageBitmap(),
                contentDescription = "some useful description",
            )
        }
        Text(
            text = "${weatherData.temp}°C",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}