package com.qii.ntsk.qii.ui.search

import androidx.appcompat.widget.SearchView
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.EpoxyController
import com.qii.ntsk.qii.model.entity.Tag

class SearchBottomSheetController(
        var tags: List<Tag> = listOf(),
        var query: String = "",
        var queryTextListener: SearchView.OnQueryTextListener,
        var tagsStateChangeListener: SearchTagsView.TagsStateChangeListener
)
    : EpoxyController() {

    @AutoModel
    lateinit var searchWordView: SearchWordViewModel_

    @AutoModel
    lateinit var tagsView: SearchTagsViewModel_

    override fun buildModels() {
        if (tags.isEmpty()) return
        searchWordView
                .defaultQuery(query)
                .onQueryTextListener(queryTextListener)
                .addTo(this)

        tagsView
                .tags(tags)
                .tagsStateChangeListener(tagsStateChangeListener)
                .addTo(this)
    }
}