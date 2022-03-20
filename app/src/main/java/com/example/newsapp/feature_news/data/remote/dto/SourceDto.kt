package com.example.newsapp.feature_news.data.remote.dto

import com.example.newsapp.feature_news.domain.model.Source

data class SourceDto(
    val id: Any,
    val name: String
) {
    fun toSource(): Source {
        return Source(
            id = id,
            name = name
        )
    }
}
