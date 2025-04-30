package com.example.imovie.utils

import androidx.room.TypeConverter
import com.example.imovie.data.model.Image
import com.example.imovie.data.model.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromRating(value: Rating): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toRating(value: String): Rating {
        return gson.fromJson(value, Rating::class.java)
    }

    @TypeConverter
    fun fromImage(value: Image?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toImage(value: String?): Image? {
        return value?.let {
            gson.fromJson(it, Image::class.java)
        }
    }
}