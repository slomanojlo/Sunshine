package rs.sloman.sunshine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import rs.sloman.sunshine.MainActivity
import rs.sloman.sunshine.R
import rs.sloman.sunshine.databinding.FragmentFavoritesBinding
import rs.sloman.sunshine.viewmodels.FavoritesViewModel


@AndroidEntryPoint
class FavoritesFragment  : Fragment(R.layout.fragment_favorites){

    private val viewModel : FavoritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity?)?.supportActionBar?.title = "Favorites"
        (activity as MainActivity?)?.nav_view?.setCheckedItem(R.id.nav_favorites)

        val binding = FragmentFavoritesBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}