package rs.sloman.sunshine.viewmodels

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import rs.sloman.sunshine.util.Constants

class SettingsViewModel @ViewModelInject constructor(private val  sharedPrefs: SharedPreferences) : ViewModel() {

    fun setDarkMode(darkModeEnabled: Boolean){
        sharedPrefs.edit()
            .putString(Constants.DARK_MODE, if(darkModeEnabled) "true" else "false")
            .apply()
    }
}