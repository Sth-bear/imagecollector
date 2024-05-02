package com.example.imagecollector.retrofit

import com.example.imagecollector.data.ImageSearch
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("image")
    suspend fun getImage(@Header("Authorization") apiKey: String, @QueryMap param: HashMap<String, String>): ImageSearch
}