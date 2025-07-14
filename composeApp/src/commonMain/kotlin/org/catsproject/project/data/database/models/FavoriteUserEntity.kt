package org.catsproject.project.data.database.models

import io.realm.kotlin.types.RealmObject


class FavoriteUserEntity: RealmObject {
    var user:String = ""
    var idCat:String = ""
}