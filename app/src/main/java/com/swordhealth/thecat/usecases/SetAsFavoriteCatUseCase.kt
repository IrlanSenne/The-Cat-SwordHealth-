package com.swordhealth.thecat.usecases

import com.swordhealth.thecat.data.entities.FavoriteRequestDto
import com.swordhealth.thecat.data.entities.ImageEntity
import com.swordhealth.thecat.data.repository.CatRepository
import kotlinx.coroutines.flow.Flow

class SetAsFavoriteCatUseCase(
    private val repository: CatRepository
) : BaseUseCase<FavoriteRequestDto, Flow<ImageEntity?>> {

    override suspend fun execute(favoriteRequestDto: FavoriteRequestDto) : Flow<ImageEntity?> {
        return repository.setAsFavorite(imageId = favoriteRequestDto.imageId, subId = favoriteRequestDto.subId)
    }
}