package com.qii.ntsk.qii.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSearchBinding
import com.qii.ntsk.qii.model.cache.TagsCache
import com.qii.ntsk.qii.model.entity.Tags

class SearchFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(SearchViewModel::class.java) }
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tagsCache = TagsCache.read()
        if (tagsCache == null) {
            viewModel.fetchTags().observe(viewLifecycleOwner, Observer { tags ->
                TagsCache.write(tags)
                initBottomSheet(tags)
            })
        } else {
            initBottomSheet(tagsCache)
        }
    }

    private fun initBottomSheet(tags: Tags) {
        binding.fragmentSearchPostsFab.setOnClickListener {
            val bottomSheet = SearchBottomSheetFragment.Builder(tags).build()
            bottomSheet.show(requireFragmentManager(), bottomSheet.tag)

        }
    }
}