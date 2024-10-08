package com.swordhealth.thecat.data.repository

import androidx.paging.Pager
import com.swordhealth.thecat.data.entities.CatEntity

interface CatRepository {
    fun getCatsPaging(): Pager<Int, CatEntity>
}