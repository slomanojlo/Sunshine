package rs.sloman.sunshine.repo

import retrofit2.Response
import rs.sloman.sunshine.BuildConfig
import rs.sloman.sunshine.model.OpenWeather
import rs.sloman.sunshine.network.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getWeatherCity(city: String) : Response<OpenWeather> = weatherApi.getWeatherCity(city, BuildConfig.api_key)
    suspend fun getWeatherLocation(lat: String, long : String, ) : Response<OpenWeather> =
        weatherApi.getWeatherLocation(lat, long, BuildConfig.api_key, "metric")

}