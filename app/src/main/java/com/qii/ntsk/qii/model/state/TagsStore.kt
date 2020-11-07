package com.qii.ntsk.qii.model.state

import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Tags

object TagsStore {
    private var tags: List<Tag> = listOf()

    fun add(tags: Tags) {
        this.tags = tags.tags
    }

    fun add(tags: List<Tag>) {
        this.tags = tags
    }

    fun update(tag: Tag) {
        tags.map {
            if (it.id == tag.id) {
                return@map tag
            }
            return@map it
        }
    }

    fun get(): Tags {
        return Tags(this.tags)
    }

    fun getList(): List<Tag> {
        return this.tags
    }

    fun getSelectedList(): List<Tag> {
        return this.tags.filter { it.isSelected }
    }

    fun clear() {
        tags = tags.map { tag ->
            tag.isSelected = false
            tag
        }
    }
}