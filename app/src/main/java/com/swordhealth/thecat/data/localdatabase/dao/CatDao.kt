package com.swordhealth.thecat.data.localdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swordhealth.thecat.data.entities.CatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Query("SELECT * FROM catEntity")
    fun getCats(): Flow<List<CatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCats(cat: CatEntity)
}