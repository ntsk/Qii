package com.qii.ntsk.qii.model.state

import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Tags

object TagsState {
    private var tags: List<Tag>? = null

    fun addAll(tags: Tags) {
        this.tags = tags.tags
    }

    fun addAll(tags: List<Tag>) {
        this.tags = tags
    }

    fun update(tag: Tag) {
        tags?.map {
            if (it.id == tag.id) {
                return@map tag
            }
            return@map it
        }
    }

    fun get(): Tags? {
        return if (this.tags != null) {
            Tags(requireNotNull(this.tags))
        } else {
            null
        }
    }

    fun getList(): List<Tag>? {
        return this.tags
    }

    fun clear() {
        tags = null
    }
}