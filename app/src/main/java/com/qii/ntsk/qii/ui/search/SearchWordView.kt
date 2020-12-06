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

    init {
        binding.viewSearchWord.isIconified = false
        binding.viewSearchWord.clearFocus()
    }

    @ModelProp
    fun setDefaultQuery(query: String) {
        binding.viewSearchWord.setQuery(query, false)
    }

    @ModelProp(ModelProp.Option.DoNotHash)
    fun setOnQueryTextListener(queryTextListener: SearchView.OnQueryTextListener) {
        binding.viewSearchWord.setOnQueryTextListener(queryTextListener)
    }
}