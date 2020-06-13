package com.qii.ntsk.qii.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSearchBinding
import com.qii.ntsk.qii.model.entity.Tags
import com.qii.ntsk.qii.model.holder.SearchQueryHolder
import com.qii.ntsk.qii.model.state.Status
import com.qii.ntsk.qii.model.state.TagsState
import com.qii.ntsk.qii.utils.QueryBuilder

class SearchFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(SearchViewModel::class.java) }
    private val controller = SearchController()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recyclerView = binding.fragmentSearchPostsRecyclerView
        recyclerView.setController(controller)
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

        if (recyclerView.adapter?.itemCount == 0) {
            binding.defaultEmpty = true
        }

        val tagsState = TagsState.get()
        if (tagsState == null) {
            viewModel.fetchTags().observe(viewLifecycleOwner, Observer { tags ->
                TagsState.addAll(tags)
                initBottomSheet(tags)
            })
        } else {
            initBottomSheet(tagsState)
        }
        return view
    }

    private fun initBottomSheet(tags: Tags) {
        binding.fragmentSearchPostsFab.setOnClickListener {
            val bottomSheet = SearchBottomSheetFragment.Builder(tags).build()
            bottomSheet.setFilterCompleteListener(object : SearchBottomSheetFragment.FilterCompleteListener {
                override fun onComplete() {
                    showPosts()
                }
            })
            bottomSheet.show(requireFragmentManager(), bottomSheet.tag)
        }
    }

    private fun showPosts() {
        val tagList = TagsState.getList() ?: return
        val query = QueryBuilder.setTags(tagList).build()

        viewModel.search(query).observe(viewLifecycleOwner, Observer {
            binding.defaultEmpty = false
            controller.submitList(it)
            SearchQueryHolder().save(query)
        })

        viewModel.networkStateObserver.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.PAGING -> {
                    controller.isLoading = true
                }
                else -> controller.isLoading = false
            }
        })
    }
}