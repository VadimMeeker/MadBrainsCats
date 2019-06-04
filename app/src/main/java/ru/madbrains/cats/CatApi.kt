package ru.madbrains.cats

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

// API
interface CatTextApi {

    @GET("bins/c9uvb")

    fun getCats(): Call<ResponseBody>
}

interface CatImageApi {

    @GET("v1/images/search")

    fun getCats(): Call<ResponseBody>
}