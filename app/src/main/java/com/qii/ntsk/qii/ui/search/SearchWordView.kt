package com.qii.ntsk.qii.ui.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.appcompat.widget.SearchView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.qii.ntsk.qii.databinding.ModelViewSearchWordBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SearchWordView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0) : RelativeLayout(context, attrs, defStyle) {

    private val binding = ModelViewSearchWordBinding.inflate(LayoutInflater.from(context), this, true)

    private var queryTextListener: SearchView.OnQueryTextListener? = null

    init {
/*
        binding.viewSearchWord.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.viewSearchWord.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    SearchQueryStore.addWord(p0)
                    return true
                }
                return false
            }
        })
*/
        binding.viewSearchWord.isIconified = false
        binding.viewSearchWord.clearFocus()
        // binding.viewSearchWord.setQuery(SearchQueryStore.getWord(), false)
    }

    @ModelProp
    fun setDefaultQuery(query: String) {
        binding.viewSearchWord.setQuery(query, false)
    }

    @ModelProp(ModelProp.Option.DoNotHash)
    fun setOnQueryTextListener(queryTextListener: SearchView.OnQueryTextListener) {
        this.queryTextListener = queryTextListener
    }
}