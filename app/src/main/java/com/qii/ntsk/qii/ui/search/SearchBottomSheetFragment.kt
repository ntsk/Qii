package com.qii.ntsk.qii.ui.search

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSearchBottomSheetBinding
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Tags
import com.qii.ntsk.qii.ui.widget.autoCleared

class SearchBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: FragmentSearchBottomSheetBinding by autoCleared()
    private var filterStateChangeListener: FilterStateChangeListener by autoCleared()
    private lateinit var controller : SearchBottomSheetController
    private lateinit var query: String
    private lateinit var tags: List<Tag>

    override fun setupDialog(dialog: Dialog, style: Int) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_search_bottom_sheet, null, false)
        binding = FragmentSearchBottomSheetBinding.bind(view)

        view.doOnLayout {
            val behavior = BottomSheetBehavior.from(view.parent as View)
            val windowHeight = Resources.getSystem().displayMetrics.heightPixels
            behavior.peekHeight = windowHeight
            behavior.skipCollapsed = true
            behavior.isHideable = false
            behavior.isDraggable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        arguments?.getSerializable(BUNDLE_KEY_SEARCH_BOTTOM_SHEET_TAGS).let { argument ->
            val arg = argument as Tags
            tags = arg.tags
        }

        arguments?.getString(BUNDLE_KEY_SEARCH_BOTTOM_SHEET_QUERY).let { argument ->
            query = argument ?: ""
        }

        binding.fragmentSearchBottomSheetRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.fragmentSearchBottomSheetClear.setOnClickListener {
            clear()
        }
        binding.fragmentSearchBottomSheetDone.setOnClickListener {
            this.filterStateChangeListener.onStateChanged(
                    query = query,
                    selectedTags = tags.filter { it.isSelected }
            )
            dismiss()
        }

        initController()

        dialog.setContentView(view)
    }

    private fun clear() {
        this.query = ""
        this.tags = tags.map {tag ->
            tag.also { it.isSelected = false }
        }
        initController()
        this.filterStateChangeListener.onStateChanged(
                query,
                listOf()
        )
    }

    private fun initController() {
        controller = SearchBottomSheetController(
                query = query,
                tags = tags,
                queryTextListener = object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText == null) {
                            return false
                        }
                        query = newText
                        filterStateChangeListener.onStateChanged(
                                query = newText,
                                selectedTags = tags.filter { it.isSelected }
                        )
                        return true
                    }
                },
                tagsStateChangeListener = object : SearchTagsView.TagsStateChangeListener {
                    override fun onStateChanged(tags: List<Tag>) {
                        this@SearchBottomSheetFragment.tags = tags
                    }
                }
        )
        binding.fragmentSearchBottomSheetRecyclerView.setController(controller)
        controller.requestModelBuild()
    }

    fun setFilterCompleteListener(filterStateChangeListener: FilterStateChangeListener) {
        this.filterStateChangeListener = filterStateChangeListener
    }

    class Builder {
        private var tags = Tags(listOf())
        private var query = ""

        fun setTags(tags: Tags): Builder {
            this.tags = tags
            return this
        }

        fun setQuery(query: String): Builder {
            this.query = query
            return this
        }

        fun build(): SearchBottomSheetFragment {
            val args = Bundle()
            args.putSerializable(BUNDLE_KEY_SEARCH_BOTTOM_SHEET_TAGS, tags)
            args.putString(BUNDLE_KEY_SEARCH_BOTTOM_SHEET_QUERY, query)
            return SearchBottomSheetFragment().also { it.arguments = args }
        }
    }

    companion object {
        const val BUNDLE_KEY_SEARCH_BOTTOM_SHEET_TAGS = "search_bottom_sheet_tags"
        const val BUNDLE_KEY_SEARCH_BOTTOM_SHEET_QUERY = "search_bottom_sheet_query"
    }

    interface FilterStateChangeListener {
        fun onStateChanged(query: String, selectedTags: List<Tag>)
    }
}