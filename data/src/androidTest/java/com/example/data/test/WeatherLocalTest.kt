package com.example.data.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.data.local.LocalDataBaseWeatherTest
import com.example.data.local.entitys.*
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@SmallTest
class WeatherLocalTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Named("test_t")
    @Inject
    lateinit var localDataBaseWeatherTest: LocalDataBaseWeatherTest


    @Test
    fun insertDataTest() {
        CoroutineScope(Dispatchers.IO).launch {
            var testWeatherModel = WeatherLocalModel(
                200, 1663589776, CoordLocal(9.0, 34.0),
                listOf(WeatherLocal(802, "clous", "test", "O1")), "stations",
                MainLocal(
                    36.89,
                    36.76,
                    36.89,
                    36.89,
                    1012,
                    27,
                ),
                10000,
                WindLocal(2.34, 223), CloudsLocal(99),
                SysLocal
                    (
                    1,
                    1197,
                    "TN",
                    1663564191,
                    1663608362
                ), 3600,
                "tunisia"
            )
            localDataBaseWeatherTest.insetWeather(testWeatherModel)
            delay(100)
            val allWeather = localDataBaseWeatherTest.getAllWeather()
            assertThat(allWeather).isNotNull()
        }
    }

    @Test
    fun deletedataTest() {
        CoroutineScope(Dispatchers.IO).launch {
            var testWeatherModel = WeatherLocalModel(
                200, 1663589776, CoordLocal(9.0, 34.0),
                listOf(WeatherLocal(802, "clous", "test", "O1")), "stations",
                MainLocal(
                    36.89,
                    36.76,
                    36.89,
                    36.89,
                    1012,
                    27,
                ),
                10000,
                WindLocal(2.34, 223), CloudsLocal(99),
                SysLocal
                    (
                    1,
                    1197,
                    "TN",
                    1663564191,
                    1663608362
                ), 3600,
                "tunisia"
            )
            localDataBaseWeatherTest.insetWeather(testWeatherModel)
            delay(200)
            localDataBaseWeatherTest.deletAllWeather()
        }
    }
}