package com.qii.ntsk.qii.model.cache

import com.qii.ntsk.qii.model.entity.Tags

object TagsCache {
    private var tags: Tags? = null

    fun write(tags: Tags) {
        this.tags = tags
    }

    fun read(): Tags? {
        return this.tags
    }

    fun clear() {
        tags = null
    }
}