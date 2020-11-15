package rs.sloman.sunshine.repo

import retrofit2.Response
import rs.sloman.sunshine.BuildConfig
import rs.sloman.sunshine.model.OpenWeather
import rs.sloman.sunshine.network.WeatherApi
import rs.sloman.sunshine.util.MetricUtil.Companion.unit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(private val weatherApi: WeatherApi) {

    suspend fun getWeatherCity(city: String) : Response<OpenWeather> =
        weatherApi.getWeatherCity(city, BuildConfig.api_key, Locale.getDefault().unit())
    suspend fun getWeatherLocation(lat: String, long : String ) : Response<OpenWeather> =
        weatherApi.getWeatherLocation(lat, long, BuildConfig.api_key, Locale.getDefault().unit())

}