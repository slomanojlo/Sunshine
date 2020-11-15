package rs.sloman.sunshine.ui

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.activity_main.*
import rs.sloman.sunshine.MainActivity
import rs.sloman.sunshine.R
import rs.sloman.sunshine.databinding.FragmentSettingsBinding
import rs.sloman.sunshine.util.Constants
import rs.sloman.sunshine.viewmodels.SettingsViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val viewModel: SettingsViewModel by activityViewModels()


    private lateinit var sharedPrefs : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity?)?.supportActionBar?.title = getString(R.string.settings)
        (activity as MainActivity?)?.nav_view?.setCheckedItem(R.id.nav_preferences)

        val binding = FragmentSettingsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        sharedPrefs = requireContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE)

        binding.swDarkMode.isChecked =
            sharedPrefs.getString(Constants.DARK_MODE, "false") == "true"


        binding.swDarkMode.setOnClickListener { switch ->
                viewModel.setDarkMode((switch as SwitchCompat).isChecked)
        }

        binding.swUnits.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.not_implemented_yet), Toast.LENGTH_SHORT).show()
        }


        return binding.root
    }



}