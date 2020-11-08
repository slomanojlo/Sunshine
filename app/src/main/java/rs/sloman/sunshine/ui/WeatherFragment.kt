package rs.sloman.sunshine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import rs.sloman.sunshine.R
import rs.sloman.sunshine.databinding.FragmentWeatherBinding
import rs.sloman.sunshine.viewmodels.WeatherViewModel

@AndroidEntryPoint
class WeatherFragment : Fragment (R.layout.fragment_weather){

    private val viewModel : WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherBinding.inflate(layoutInflater)

        binding.viewModel = viewModel

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}