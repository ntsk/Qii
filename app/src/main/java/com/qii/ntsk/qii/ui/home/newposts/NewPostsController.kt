package com.qii.ntsk.qii.ui.home.newposts

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.qii.ntsk.qii.ModelPostItemBindingModel_
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.ui.MainActivity
import com.qii.ntsk.qii.ui.PostDetailFragment
import com.qii.ntsk.qii.utils.DateFormatUtil

class NewPostsController : PagedListEpoxyController<Post>() {

    override fun buildItemModel(currentPosition: Int, item: Post?): EpoxyModel<*> {
        return ModelPostItemBindingModel_()
                .id(item?.id)
                .title(item?.title)
                .description(item?.body)
                .userImageUrl(item?.user?.profileImageUrl)
                .clickListener(View.OnClickListener {
                    if (item?.url != null) {
                        val activity = it.context as MainActivity
                        activity.showPostDetail(item)
                    }
                })
                .date(DateFormatUtil.formatTimeAndDate(item?.createdAt ?: ""))
    }
}