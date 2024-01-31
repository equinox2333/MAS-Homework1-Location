package com.alex.location.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Interface Definition
 */
interface ApiService {
    @Headers("x-access-token:openuv-8bi47rlpgdv1yy-io")
    @GET("api/v1/uv?alt=100&dt=")
    fun getValue(@Query("lat") lat: Double, @Query("lng") lng: Double): Call<ResultBean>
}