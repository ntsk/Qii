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
import com.qii.ntsk.qii.datasource.holder.SearchQueryHolder
import com.qii.ntsk.qii.datasource.repository.PostsRepository
import com.qii.ntsk.qii.datasource.repository.TagsRepository
import com.qii.ntsk.qii.model.entity.Tags
import com.qii.ntsk.qii.model.state.Status
import com.qii.ntsk.qii.model.state.TagsStore
import com.qii.ntsk.qii.utils.CustomTabsStarter
import com.qii.ntsk.qii.utils.QueryBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var controller: SearchController
    private lateinit var binding: FragmentSearchBinding

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

        val tags = TagsStore.getList()
        if (tags.isEmpty()) {
            viewModel.fetchTags().observe(viewLifecycleOwner, Observer {
                TagsStore.add(it)
                initBottomSheet(TagsStore.get())
            })
        } else {
            initBottomSheet(TagsStore.get())
        }
    }

    private fun initSearchView() {
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.fragmentSearchSearchView.clearFocus()
                viewModel.search(query ?: "").observe(viewLifecycleOwner, Observer {
                    binding.defaultEmpty = false
                    controller.submitList(it)
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }
        binding.fragmentSearchSearchView.setOnQueryTextListener(queryTextListener)
    }

    private fun initBottomSheet(tags: Tags) {
        binding.fragmentSearchPostsFab.setOnClickListener {
            val bottomSheet = SearchBottomSheetFragment.Builder(tags).build()
            bottomSheet.setFilterCompleteListener(object : SearchBottomSheetFragment.FilterStateChangeListener {
                override fun onStateChanged() {
                    showPosts()
                }
            })
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun showPosts() {
        val tagList = TagsStore.getList() ?: listOf()
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