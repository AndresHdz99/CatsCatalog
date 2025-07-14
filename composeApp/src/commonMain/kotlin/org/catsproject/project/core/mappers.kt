package org.catsproject.project.core

import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity
import org.catsproject.project.data.model.CatInformation
import org.catsproject.project.data.model.CatsImage
import org.catsproject.project.data.network.networkmodel.CatsModelServicesItem

fun CatsModelServicesItem.toServiceByDB(pg:Int) : CatsEntity{
    return CatsEntity().apply {
        _id = this@toServiceByDB.id
        url = this@toServiceByDB.url
        name = this@toServiceByDB.breeds.first().name
        temperament = this@toServiceByDB.breeds.first().temperament
        description = this@toServiceByDB.breeds.first().description
        energyLevel = this@toServiceByDB.breeds.first().energy_level
        intelligence = this@toServiceByDB.breeds.first().intelligence
        origin = this@toServiceByDB.breeds.first().origin ?: ""
        life_span = this@toServiceByDB.breeds.first().life_span ?: ""
        page = pg
    }
}

fun CatResulEntity.toDomain() = CatsImage(
    id = _id,
    url = url,
    name = name,
    temperament = temperament,
    page = page,
    favorite = favorite
)


fun CatResulEntity.toDomainInformation() : CatInformation{
    return CatInformation(
        url = url,
        name = name,
        information = description,
        favorite = favorite,
        energyLevel =  energyLevel.toFloat() / 10f,
        intelligence = intelligence.toFloat() / 10f,
        origin = origin,
        life_span = life_span
    )
}


fun CatsEntity.toCatResultEntity(favorite: Boolean = false): CatResulEntity {
    return CatResulEntity(
        _id = this._id,
        url = this.url,
        name = this.name,
        temperament = this.temperament,
        description = this.description,
        energyLevel = this.energyLevel,
        intelligence = this.intelligence,
        page = this.page,
        origin = origin,
        favorite = favorite,
        life_span = this.life_span
    )
}

fun CatsEntity.copyFrom(source: CatsEntity) {
    page = source.page
    name = source.name
    description = source.description
    temperament = source.temperament
    energyLevel = source.energyLevel
    intelligence = source.intelligence
    life_span = source.life_span
    origin = source.origin
    url = source.url
}
