package com.qii.ntsk.qii.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSearchBinding
import com.qii.ntsk.qii.model.entity.Tags
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
        binding.defaultEmpty = true

        val recyclerView = binding.fragmentSearchPostsRecyclerView
        recyclerView.setController(controller)
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        controller.addModelBuildListener {
            if (binding.isLoading) {
                binding.isLoading = false
                recyclerView.scheduleLayoutAnimation()
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val tagsState = TagsState.get()
        if (tagsState == null) {
            viewModel.fetchTags().observe(viewLifecycleOwner, Observer { tags ->
                TagsState.addAll(tags)
                initBottomSheet(tags)
            })
        } else {
            initBottomSheet(tagsState)
        }
    }

    private fun initBottomSheet(tags: Tags) {
        binding.fragmentSearchPostsFab.setOnClickListener {
            val bottomSheet = SearchBottomSheetFragment.Builder(tags).build()
            bottomSheet.setFilterCompleteListener(object : SearchBottomSheetFragment.FilterCompleteListener {
                override fun onComplete() {
                    val tagList = TagsState.getList() ?: return
                    binding.isLoading = true
                    viewModel.search(QueryBuilder.setTags(tagList).build()).observe(viewLifecycleOwner, Observer {
                        binding.defaultEmpty = false
                        binding.showError = false

                        controller.submitList(it)
                        controller.requestModelBuild()
                    })

                    viewModel.errorObserver.observe(viewLifecycleOwner, Observer {
                        binding.showError = true
                        binding.isLoading = false
                        binding.defaultEmpty = false
                    })
                }
            })
            bottomSheet.show(requireFragmentManager(), bottomSheet.tag)
        }
    }
}