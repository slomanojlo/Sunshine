package rs.sloman.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import rs.sloman.sunshine.repo.Repo
import timber.log.Timber

class FavoritesViewModel  @ViewModelInject constructor(private val repo : Repo) : ViewModel() {

init {
    Timber.d("fav init")
}

}