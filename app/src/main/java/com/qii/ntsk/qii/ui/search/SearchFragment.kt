package com.qii.ntsk.qii.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        return view
    }
}