package com.swordhealth.thecat.data.localdatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.swordhealth.thecat.data.entities.ImageEntity

class Converters {

    @TypeConverter
    fun fromImageEntity(imageEntity: ImageEntity?): String? {
        return if (imageEntity == null) null else Gson().toJson(imageEntity)
    }

    @TypeConverter
    fun toImageEntity(imageEntityString: String?): ImageEntity? {
        return if (imageEntityString == null) null else Gson().fromJson(imageEntityString, object : TypeToken<ImageEntity>() {}.type)
    }
}