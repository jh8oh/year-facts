package dev.ohjiho.yearfacts.data.network

import dev.ohjiho.yearfacts.BuildConfig
import dev.ohjiho.yearfacts.data.model.YearFact
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface NumbersEndpoint {

    @Headers("x-rapidapi-key: ${BuildConfig.API_KEY}", "x-rapidapi-host: numbersapi.p.rapidapi.com")
    @GET("{Year}/year")
    fun getYear(
        @Path("Year") year: String,
        @Query("json") json: String = "true",
        @Query("fragment") fragment: String = "true"
    ): Call<YearFact>

    @Headers("x-rapidapi-key: ${BuildConfig.API_KEY}", "x-rapidapi-host: numbersapi.p.rapidapi.com")
    @GET("random/year")
    fun getRandom(
        @Query("json") json: String = "true",
        @Query("fragment") fragment: String = "true"
    ): Call<YearFact>
}