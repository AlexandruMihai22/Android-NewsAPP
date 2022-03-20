package com.example.newsapp.feature_news.data.local

import androidx.room.TypeConverter
import com.example.newsapp.feature_news.domain.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}