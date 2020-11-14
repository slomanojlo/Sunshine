package rs.sloman.sunshine.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import rs.sloman.sunshine.Constants
import rs.sloman.sunshine.R
import rs.sloman.sunshine.TrackingUtil
import rs.sloman.sunshine.databinding.FragmentWeatherBinding
import rs.sloman.sunshine.viewmodels.WeatherViewModel
import timber.log.Timber

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather), EasyPermissions.PermissionCallbacks {

    companion object {
        const val REQUEST_CODE_LOCATION_PERMISSION = 0
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: WeatherViewModel by viewModels()

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        requestPermissions()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                Timber.d("${location?.longitude} ${location?.latitude}")
                viewModel.getWeatherCity(
                    location?.latitude.toString(),
                    location?.longitude.toString()
                )

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

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show()
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
        //TODO Permission Granted
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