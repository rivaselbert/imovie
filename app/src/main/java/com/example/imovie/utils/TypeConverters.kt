package com.example.imovie.utils

import androidx.room.TypeConverter
import com.example.imovie.data.model.Image
import com.example.imovie.data.model.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return if (value == null) null else gson.fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromRating(value: Rating?): String? {
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toRating(value: String?): Rating? {
        return if (value == null) null else gson.fromJson(value, Rating::class.java)
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