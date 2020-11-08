package com.qii.ntsk.qii.ui.user

import android.view.View
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.qii.ntsk.qii.ModelPostItemBindingModel_
import com.qii.ntsk.qii.ModelViewListLoadingBindingModel_
import com.qii.ntsk.qii.model.entity.Post

class UserItemsController(private val onItemClick: (Post) -> Unit) : PagedListEpoxyController<Post>() {
    var isLoading = false
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildItemModel(currentPosition: Int, item: Post?): EpoxyModel<*> {
        return ModelPostItemBindingModel_()
                .id(item?.id)
                .post(item)
                .clickListener(View.OnClickListener {
                    if (item?.url != null) {
                        onItemClick(item)
                    }
                })
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        val newModels = models.toMutableList()
        if (isLoading) {
            newModels.add(ModelViewListLoadingBindingModel_().id("loading"))
        }
        super.addModels(newModels)
    }
}