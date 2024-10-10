package com.swordhealth.thecat.ui.mappers

import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.CatUI
import com.swordhealth.thecat.data.entities.ImageEntity
import com.swordhealth.thecat.data.entities.ImageUI

fun CatEntity.toUI(isFromFavourite: Boolean = false) = CatUI(
    name = this.name,
    origin = this.origin,
    temperament = this.temperament,
    description = this.description,
    lifeSpan = this.lifeSpan,
    idFavorite = this.idFavorite,
    image = ImageUI(
        id = this.image?.id,
        width = this.image?.width,
        height = this.image?.height,
        url = this.image?.url
    ),
    isFromFavourites = isFromFavourite
)

fun CatUI.toEntity() = CatEntity(
    id = this.id,
    name = this.name,
    origin = this.origin,
    temperament = this.temperament,
    description = this.description,
    lifeSpan = this.lifeSpan,
    idFavorite = this.idFavorite,
    image = ImageEntity(
        id = this.image?.id,
        width = this.image?.width,
        height = this.image?.height,
        url = this.image?.url
    )
)
