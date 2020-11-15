package rs.sloman.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import rs.sloman.sunshine.model.OpenWeather
import rs.sloman.sunshine.repo.Repo
import timber.log.Timber

class WeatherViewModel @ViewModelInject constructor(private val repo : Repo) : ViewModel() {

    val openWeather : MutableLiveData<OpenWeather> = MutableLiveData()
    val errorMessage : MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("init")
        Timber.d(repo.hashCode().toString())

    }


    fun getWeatherLocation(lat: String, lon : String) {

        viewModelScope.launch {
            val response = repo.getWeatherLocation(lat, lon)

            Timber.d(response.isSuccessful.toString())

            if (response.isSuccessful) {
                openWeather.value = response.body()
            }

        }
    }

    fun getWeatherCity(city : String) {

        viewModelScope.launch {
            val response = repo.getWeatherCity(city = city)

            Timber.d(response.isSuccessful.toString())

            if (response.isSuccessful) {
                openWeather.value = response.body()
            } else {
                try {
                    val errorJson = JSONObject(response.errorBody()!!.charStream().readText())
                    errorMessage.value = errorJson.getString("message")

                } catch (e : Exception){
                        e.printStackTrace()
                }
            }

        }
    }

}
