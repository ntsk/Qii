package com.qii.ntsk.qii.ui.home.newposts

import com.airbnb.epoxy.EpoxyController
import com.qii.ntsk.qii.model.entity.Post
import com.qii.ntsk.qii.modelPostItem
import com.qii.ntsk.qii.utils.DateFormatUtil

class NewPostsController : EpoxyController(){
    val posts = ArrayList<Post>()

    override fun buildModels() {
        posts.forEach{
            modelPostItem {
                id(it.id)
                title(it.title)
                description(it.body)
                date(DateFormatUtil.formatTimeAndDate(it.created_at))
            }
        }
    }

    fun addPosts(posts: List<Post>) {
        this.posts.addAll(posts)
    }
}