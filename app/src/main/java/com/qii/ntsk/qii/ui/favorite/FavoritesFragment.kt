package com.qii.ntsk.qii.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private val controller = FavoritesController()
    private lateinit var binding : FragmentFavoritesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        binding = FragmentFavoritesBinding.bind(view)

        val recyclerView = binding.fragmentFavoriteRecyclerView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)
    }
}