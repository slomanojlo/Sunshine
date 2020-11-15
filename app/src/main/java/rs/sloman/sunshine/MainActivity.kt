package rs.sloman.sunshine

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_weather ->
                    this.findNavController(R.id.nav_host_fragment)
                        .navigate(R.id.weatherFragment)
                R.id.nav_favorites -> this.findNavController(R.id.nav_host_fragment)
                    .navigate(R.id.favoritesFragment)
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
//            val currentFragment = findNavController(R.id.navHostFragment).currentDestination?.label
//
//            currentFragment?.let {
//                if (it == "ProductListFragment") {
//                    finish()
//                } else {
//                    Navigation.findNavController(this, R.id.navHostFragment)
//                        .navigate(R.id.productListFragment)
//                }
        }
    }
}
