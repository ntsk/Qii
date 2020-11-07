package com.qii.ntsk.qii.ui.search

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSearchBottomSheetBinding
import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Tags
import com.qii.ntsk.qii.model.state.TagsStore

class SearchBottomSheetFragment : BottomSheetDialogFragment() {
    private var filterStateChangeListener: FilterStateChangeListener? = null
    private val controller = SearchBottomSheetController()
    private lateinit var binding: FragmentSearchBottomSheetBinding
    private lateinit var tags: List<Tag>

    override fun setupDialog(dialog: Dialog, style: Int) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_search_bottom_sheet, null, false)
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val behavior = BottomSheetBehavior.from(view.parent as View)
            behavior.peekHeight = view.height
        }

        arguments?.get(BUNDLE_KEY_SEARCH_BOTTOM_SHEET).let { argument ->
            val arg = argument as Tags
            tags = arg.tags
        }

        controller.tags = tags
        binding = FragmentSearchBottomSheetBinding.bind(view)
        binding.fragmentSearchBottomSheetRecyclerView.setController(controller)
        binding.fragmentSearchBottomSheetRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.fragmentSearchBottomSheetClear.setOnClickListener {
            clear()
        }
        binding.fragmentSearchBottomSheetDone.setOnClickListener {
            this.filterStateChangeListener?.onStateChanged()
            dismiss()
        }

        controller.requestModelBuild()
        dialog.setContentView(view)
    }

    private fun clear() {
        val controller = SearchBottomSheetController()
        TagsStore.clear()
        controller.tags = TagsStore.getList()
        binding.fragmentSearchBottomSheetRecyclerView.setController(controller)
        controller.requestModelBuild()
        this.filterStateChangeListener?.onStateChanged()
    }

    fun setFilterCompleteListener(filterStateChangeListener: FilterStateChangeListener) {
        this.filterStateChangeListener = filterStateChangeListener
    }

    class Builder(tags: Tags) {

        private val args: Bundle = Bundle()

        init {
            args.putSerializable(BUNDLE_KEY_SEARCH_BOTTOM_SHEET, tags)
        }

        fun build(): SearchBottomSheetFragment {
            return SearchBottomSheetFragment().also { it.arguments = args }
        }
    }

    companion object {
        const val BUNDLE_KEY_SEARCH_BOTTOM_SHEET = "search_bottom_sheet"
    }

    interface FilterStateChangeListener {
        fun onStateChanged()
    }
}