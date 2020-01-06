package com.qii.ntsk.qii.ui.user

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.qii.ntsk.qii.ModelPostItemBindingModel_
import com.qii.ntsk.qii.ModelViewListLoadingBindingModel_
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.ui.MainActivity
import com.qii.ntsk.qii.utils.DateFormatUtil

class UserItemsController : PagedListEpoxyController<Post>() {
    var isLoading = false
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

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

    override fun addModels(models: List<EpoxyModel<*>>) {
        val newModels = models.toMutableList()
        if (isLoading) {
            newModels.add(ModelViewListLoadingBindingModel_().id("loading"))
        }
        super.addModels(newModels)
    }
}