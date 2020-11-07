package com.qii.ntsk.qii.ui.search

import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.EpoxyController
import com.qii.ntsk.qii.model.entity.Tag

class SearchBottomSheetController : EpoxyController() {
    var tags: List<Tag> = listOf()

    @AutoModel
    lateinit var searchWordView: SearchWordViewModel_

    @AutoModel
    lateinit var tagsView: TagsViewModel_

    override fun buildModels() {
        if (tags.isEmpty()) return
        searchWordView.addTo(this)
        tagsView.id(tags.hashCode()).tags(tags).addTo(this)
    }
}