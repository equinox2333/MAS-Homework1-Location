package com.alex.location.net

/**
 * Web request response raw bean
 */
data class ResultBean(
    val result: Result,
)

data class Result(
    val ozone: Double,
    val ozone_time: String,
    val safe_exposure_time: SafeExposureTime,
    val sun_info: SunInfo,
    val uv: Double,
    val uv_max: Double,
    val uv_max_time: String,
    val uv_time: String,
)

data class SafeExposureTime(
    val st1: Any,
    val st2: Any,
    val st3: Any,
    val st4: Any,
    val st5: Any,
    val st6: Any,
)

data class SunInfo(
    val sun_position: SunPosition,
    val sun_times: SunTimes,
)

data class SunPosition(
    val altitude: Double,
    val azimuth: Double,
)

data class SunTimes(
    val dawn: String,
    val dusk: String,
    val goldenHour: String,
    val goldenHourEnd: String,
    val nadir: String,
    val nauticalDawn: String,
    val nauticalDusk: String,
    val night: String,
    val nightEnd: String,
    val solarNoon: String,
    val sunrise: String,
    val sunriseEnd: String,
    val sunset: String,
    val sunsetStart: String,
)