package com.swordhealth.thecat.ui.mappers

import com.swordhealth.thecat.data.entities.CatEntity
import com.swordhealth.thecat.data.entities.CatUI
import com.swordhealth.thecat.data.entities.ImageEntity
import com.swordhealth.thecat.data.entities.ImageUI

fun CatEntity.toUI() = CatUI(
    name = this.name,
    origin = this.origin,
    temperament = this.temperament,
    description = this.description,
    lifeSpan = this.lifeSpan,
    image = ImageUI(
        id = this.id,
        width = this.image?.width,
        height = this.image?.height,
        url = this.image?.url
    )
)

fun CatUI.toEntity() = CatEntity(
    id = this.image?.id,
    name = this.name,
    origin = this.origin,
    temperament = this.temperament,
    description = this.description,
    lifeSpan = this.lifeSpan,
    image = ImageEntity(
        id = this.image?.id,
        width = this.image?.width,
        height = this.image?.height,
        url = this.image?.url
    )
)
