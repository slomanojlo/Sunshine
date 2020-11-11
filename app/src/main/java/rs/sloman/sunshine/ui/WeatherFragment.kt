package rs.sloman.sunshine.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
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

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        requestPermissions()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    Timber.d("${location?.longitude} ${location?.latitude}")
                    viewModel.getWeatherLocation(location?.latitude.toString(), location?.longitude.toString())

                }

        return binding.root
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