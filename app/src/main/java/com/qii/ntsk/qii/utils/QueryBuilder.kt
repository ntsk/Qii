package com.qii.ntsk.qii.utils

import com.qii.ntsk.qii.model.entity.Tag


class QueryBuilder {
    companion object {
        private var tags: List<Tag> = listOf()
        private var word: String = ""

        fun setTags(tags: List<Tag>): QueryBuilder.Companion {
            this.tags = tags
            return this
        }

        fun setWord(word: String): QueryBuilder.Companion {
            this.word = word
            return this
        }

        fun build(): String {
            return if (tags.isNotEmpty()) {
                if (word.isNotEmpty()) {
                    tags.filter { it.isSelected }.map { it.id }.joinToString("+OR+") { "$word+AND+tag:$it" }
                } else {
                    tags.filter { it.isSelected }.map { it.id }.joinToString("+OR+") { "tag:$it" }
                }
            } else {
                word
            }
        }
    }
}