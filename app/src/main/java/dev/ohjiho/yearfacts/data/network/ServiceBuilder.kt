package dev.ohjiho.yearfacts.data.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {

    private const val BASE_URL = "https://numbersapi.p.rapidapi.com/"
    private val gson by lazy { GsonBuilder().setLenient().create() }
    private val client by lazy { OkHttpClient.Builder().build() }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}