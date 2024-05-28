package ru.smak.clock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

class MainViewModel : ViewModel()
{
    var showDialog: Boolean by mutableStateOf(false)

    private val mainClock: ZonedDateTime
        get() = ZonedDateTime.now(ZoneOffset.UTC)

    var hours by mutableFloatStateOf(0f)
    var minutes by mutableFloatStateOf(0f)
    var seconds by mutableFloatStateOf(0f)

    val clockList = mutableStateListOf<Pair<String, ArrowAngles>>()

    private fun getHoursAngle(time: LocalTime): Float =
        time.hour.toFloat() * 30f + time.minute.toFloat() / 2f + time.second.toFloat() / 120f

    private fun getMinutesAngle(time: LocalTime): Float =
        time.minute.toFloat() * 6f + time.second.toFloat() / 10f

    private fun getSecondsAngle(time: LocalTime): Float =
        time.second.toFloat() * 6f

    init{
        viewModelScope.launch {
            while(true) {
                hours = getHoursAngle(mainClock.toLocalTime())
                minutes = getMinutesAngle(mainClock.toLocalTime())
                seconds = getSecondsAngle(mainClock.toLocalTime())
                delay(500)
            }
        }
        addWatch("Москва", 3, 0)
    }

    fun addWatch(name: String, hours: Int, minutes: Int){
        clockList.add(
            Pair(
                name,
                ArrowAngles(
                    getHoursAngle(LocalTime.of(hours, minutes, 0)),
                    getMinutesAngle(LocalTime.of(hours, minutes, 0))
                )
            )
        )
    }
}