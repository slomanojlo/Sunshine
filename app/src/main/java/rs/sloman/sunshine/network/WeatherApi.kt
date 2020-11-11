package rs.sloman.sunshine.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import rs.sloman.sunshine.model.OpenWeather

interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherCity(
        @Query("q") city_name : String,
        @Query("appid") appId : String
    ): Response<OpenWeather>

    @GET("weather")
    suspend fun getWeatherLocation(
        @Query("lat") latitude : String,
        @Query("lon") longitude : String,
        @Query("appid") appId : String,
        @Query("units") units : String,
    ): Response<OpenWeather>

}
