package com.qii.ntsk.qii.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSearchBinding
import com.qii.ntsk.qii.datasource.repository.PostsRepository
import com.qii.ntsk.qii.datasource.repository.TagsRepository
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.state.Status
import com.qii.ntsk.qii.ui.widget.autoCleared
import com.qii.ntsk.qii.utils.CustomTabsStarter
import com.qii.ntsk.qii.utils.QueryBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchBottomSheetFragment.FilterStateChangeListener {
    private val viewModel: SearchViewModel by viewModels()
    private var binding: FragmentSearchBinding by autoCleared()
    private lateinit var controller: SearchController

    @Inject
    lateinit var postsRepository: PostsRepository

    @Inject
    lateinit var tagsRepository: TagsRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        controller = SearchController { post ->
            CustomTabsStarter.start(requireContext(), post.url)
        }
        val recyclerView = binding.fragmentSearchPostsRecyclerView
        recyclerView.setController(controller)

        if (recyclerView.adapter?.itemCount == 0) {
            binding.defaultEmpty = true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView()
        initBottomSheet()
    }

    override fun onStateChanged(query: String, selectedTags: List<Tag>) {
        viewModel.searchWord = query
        viewModel.selectedTags = selectedTags.toMutableList()
        binding.fragmentSearchSearchView.setQuery(query, false)
        search()
    }

    private fun initSearchView() {
        binding.fragmentSearchSearchView.let { view ->
            view.isIconified = false
            val queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    view.clearFocus()
                    viewModel.searchWord = query ?: ""
                    search()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
            view.setOnQueryTextListener(queryTextListener)
            view.setQuery(viewModel.searchWord, false)
            view.clearFocus()
        }
    }

    private fun initBottomSheet() {
        val tags = viewModel.tags.tags
        if (tags.isNotEmpty()) {
            initFab()
            return
        }
        viewModel.fetchTags().observe(viewLifecycleOwner, Observer {
            initFab()
        })
    }

    private fun initFab() {
        binding.fragmentSearchPostsFab.setOnClickListener {
            val bottomSheet = SearchBottomSheetFragment.Builder()
                    .setQuery(viewModel.searchWord)
                    .setTags(viewModel.tags)
                    .build()
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun search() {
        val searchWord = viewModel.searchWord
        val tagList = viewModel.selectedTags
        val query = QueryBuilder()
                .setWord(searchWord)
                .setTags(tagList)
                .build()

        viewModel.search(query).observe(viewLifecycleOwner, Observer {
            binding.defaultEmpty = false
            controller.submitList(it)
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