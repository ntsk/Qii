package com.qii.ntsk.qii.ui.search

import com.airbnb.epoxy.EpoxyController
import com.qii.ntsk.qii.model.entity.Tag

class SearchBottomSheetController : EpoxyController() {

    var tags: List<Tag> = listOf()

    override fun buildModels() {
        if (tags.isEmpty()) return

        TagsViewModel_()
                .id(tags.hashCode())
                .tags(tags)
                .addTo(this)
    }
}