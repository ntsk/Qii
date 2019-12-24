package com.qii.ntsk.qii.ui.search

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.qii.ntsk.qii.R
import com.qii.ntsk.qii.databinding.FragmentSearchBottomSheetBinding
import com.qii.ntsk.qii.model.entity.Tags

class SearchBottomSheetFragment : BottomSheetDialogFragment() {
    private var filterCompleteListener: FilterCompleteListener? = null

    override fun setupDialog(dialog: Dialog, style: Int) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_search_bottom_sheet, null, false)
        val binding = FragmentSearchBottomSheetBinding.bind(view)
        view.viewTreeObserver.addOnGlobalLayoutListener {
            val behavior = BottomSheetBehavior.from(view.parent as View)
            //val margin = 56 * resources.displayMetrics.density.toInt()
            behavior.peekHeight = view.height
        }

        val controller = SearchBottomSheetController()
        binding.fragmentSearchBottomSheetRecyclerView.setController(controller)
        binding.fragmentSearchBottomSheetRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.fragmentSearchBottomSheetDone.setOnClickListener {}

        arguments?.get(BUNDLE_KEY_SEARCH_BOTTOM_SHEET).let {
            val arg = it as Tags
            controller.tags = arg.tags
            controller.requestModelBuild()
        }

        dialog.setContentView(view)
    }

    fun setFilterCompleteListener(filterCompleteListener: FilterCompleteListener) {
        this.filterCompleteListener = filterCompleteListener
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

    interface FilterCompleteListener {
        fun onComplete()
    }
}