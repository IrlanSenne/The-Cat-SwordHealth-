package com.swordhealth.thecat.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "catEntity")
data class CatLocalEntity(
    @PrimaryKey
    val id: Int,
    val test: String
)
