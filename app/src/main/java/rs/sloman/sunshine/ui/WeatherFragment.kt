package rs.sloman.sunshine.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import rs.sloman.sunshine.MainActivity
import rs.sloman.sunshine.R
import rs.sloman.sunshine.databinding.FragmentWeatherBinding
import rs.sloman.sunshine.util.Constants
import rs.sloman.sunshine.util.TrackingUtil
import rs.sloman.sunshine.viewmodels.WeatherViewModel
import timber.log.Timber

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather), EasyPermissions.PermissionCallbacks {


    companion object {
        const val REQUEST_CODE_LOCATION_PERMISSION = 0
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        (activity as MainActivity?)?.supportActionBar?.title = "Sunshine"
        (activity as MainActivity?)?.nav_view?.setCheckedItem(R.id.nav_weather)

        val binding = FragmentWeatherBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        requestPermissions()

        if (viewModel.openWeather.value == null) {
            if (viewModel.favCity.value != null && viewModel.favCity.value!!.city.isNotEmpty()) {
                viewModel.getWeatherCity(viewModel.favCity.value!!.city)
            } else {
                getLocationByCoordinates()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if (it == Constants.ERR_CITY_NOT_FOUND) {
                Snackbar.make(
                    binding.root,
                    requireContext().getString(R.string.please_select_valid_city),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.openWeather.observe(viewLifecycleOwner) {
            CoroutineScope(IO).launch {
                val isFavorite = viewModel.findFavoriteCity(it.name)
                viewModel.isFavoriteCity.postValue(isFavorite)
            }

        }

        viewModel.favCity?.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.getWeatherCity(it.city)
            }
        }
        
        binding.ivFavorite.setOnClickListener {
            viewModel.insertOrRemoveFavCity()
        }

        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun getLocationByCoordinates() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                Timber.d("${location?.longitude} ${location?.latitude}")

                if (location != null) {
                    viewModel.getWeatherLocation(
                        location.latitude.toString(),
                        location.longitude.toString()
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.enable_location_services),
                        LENGTH_LONG
                    ).show()
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Find a city"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    viewModel.getWeatherCity(query)
                }
                searchView.clearFocus()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun requestPermissions() {
        if (TrackingUtil.hasLocationPermissions(requireContext())) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.accept_permissions),
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.accept_permissions),
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        getLocationByCoordinates()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}