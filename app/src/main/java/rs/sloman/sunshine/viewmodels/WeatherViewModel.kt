package rs.sloman.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.sloman.sunshine.repo.Repo
import timber.log.Timber

class WeatherViewModel @ViewModelInject constructor(private val repo : Repo) : ViewModel() {


    init {

        viewModelScope.launch {

        val response = repo.getWeather("Belgrade")

        Timber.d(" "  + response.isSuccessful)

        }

    }

}
