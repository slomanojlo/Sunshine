package rs.sloman.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.sloman.sunshine.model.OpenWeather
import rs.sloman.sunshine.repo.Repo
import timber.log.Timber

class WeatherViewModel @ViewModelInject constructor(private val repo : Repo) : ViewModel() {

    var openWeather : MutableLiveData<OpenWeather> = MutableLiveData()


    fun getWeatherLocation(lat: String, lon : String) {

        viewModelScope.launch {
            val response = repo.getWeatherLocation(lat, lon)

            Timber.d(response.isSuccessful.toString())

            if (response.isSuccessful) {
                openWeather.value = response.body()
            }

        }
    }

}
