package com.swordhealth.thecat.data.localdatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.swordhealth.thecat.data.entities.CatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Query("SELECT * FROM cats_list")
    fun getCats(): Flow<List<CatEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCats(cats: List<CatEntity>)

    @Query("DELETE FROM cats_list WHERE id IN (:catIds)")
    suspend fun deleteCatsByIds(catIds: List<String>)

    @Query("UPDATE cats_list SET idFavorite = :idFavorite WHERE id = :name")
    suspend fun updateFavoriteStatus(name: String, idFavorite: String?)
}