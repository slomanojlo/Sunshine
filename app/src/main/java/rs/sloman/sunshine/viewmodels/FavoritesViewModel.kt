package rs.sloman.sunshine.viewmodels

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.sloman.sunshine.model.Favorite
import rs.sloman.sunshine.repo.Repo
import timber.log.Timber

class FavoritesViewModel  @ViewModelInject constructor(private val repo : Repo, sharedPreferences: SharedPreferences) : ViewModel() {

    val favList : LiveData<List<Favorite>> = repo.getAllFavorites()
    val favCity : LiveData<Favorite?> = repo.getFavoriteCity()

init {
    Timber.d("fav init")
    Timber.d("favList size ${favList.value?.size}")
}

    fun removeFavCity(favorite: Favorite){
        viewModelScope.launch {
                repo.removeFavorite(favorite)
        }
    }

    fun updateFavoriteCity(favorite: Favorite){
        viewModelScope.launch {
            repo.updateFavoriteCity(favorite)
        }
    }

    fun getFavFromPosition(position: Int): Favorite = favList.value!![position]

}