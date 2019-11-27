package com.qii.ntsk.qii.ui.home.popularposts

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.qii.ntsk.qii.ModelPostItemBindingModel_
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.utils.DateFormatUtil

class PopularPostsController: PagedListEpoxyController<Post>(){

    override fun buildItemModel(currentPosition: Int, item: Post?): EpoxyModel<*> {
        return ModelPostItemBindingModel_()
                .id(item?.id)
                .title(item?.title)
                .description(item?.body)
                .date(DateFormatUtil.formatTimeAndDate(item?.created_at ?: ""))
    }
}