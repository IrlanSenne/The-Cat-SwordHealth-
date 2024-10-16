package com.swordhealth.thecat.data.localdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.localdatabase.dao.CatDao

@TypeConverters(value = [Converters::class])
@Database(entities = [CatEntity::class], version = 1)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}