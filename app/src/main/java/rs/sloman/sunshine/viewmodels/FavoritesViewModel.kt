package rs.sloman.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import rs.sloman.sunshine.repo.Repo

class FavoritesViewModel  @ViewModelInject constructor(private val repo : Repo) : ViewModel() {



}