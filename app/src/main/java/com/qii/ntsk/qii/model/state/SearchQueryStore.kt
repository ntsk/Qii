package com.qii.ntsk.qii.model.state

import com.qii.ntsk.qii.model.entity.Tag
import com.qii.ntsk.qii.model.entity.Tags

object SearchQueryStore {
    private var word: String = ""
    private var tags: List<Tag> = listOf()

    fun addWord(word: String) {
        this.word = word
    }

    fun addTags(tags: Tags) {
        this.tags = tags.tags
    }

    fun addTags(tags: List<Tag>) {
        this.tags = tags
    }

    fun updateTags(tag: Tag) {
        tags.map {
            if (it.id == tag.id) {
                return@map tag
            }
            return@map it
        }
    }

    fun getWord(): String {
        return word;
    }

    fun getTags(): Tags {
        return Tags(this.tags)
    }

    fun getTagsList(): List<Tag> {
        return this.tags
    }

    fun getSelectedTagsList(): List<Tag> {
        return this.tags.filter { it.isSelected }
    }

    fun clearWord() {
        word = ""
    }

    fun clearTags() {
        tags = tags.map { tag ->
            tag.isSelected = false
            tag
        }
    }
}