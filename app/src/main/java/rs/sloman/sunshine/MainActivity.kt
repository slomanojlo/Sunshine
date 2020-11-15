package rs.sloman.sunshine

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


        @set:Inject
        var isDarkModeEnabled: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_weather ->
                    this.findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.weatherFragment)
                R.id.nav_favorites -> this.findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.favoritesFragment)
                R.id.nav_preferences -> this.findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.settingsFragment)
            }
            drawerLayout.closeDrawer(GravityCompat.START, true)
            true
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar as Toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }


    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(nav_view)) {
            drawerLayout.closeDrawer(nav_view)
        } else {
            val currentFragment =
                findNavController(R.id.nav_host_fragment).currentDestination?.label

            currentFragment?.let {
                if (it == "WeatherFragment") {
                    finish()
                } else {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.weatherFragment)
                }
            }
        }
    }
}
