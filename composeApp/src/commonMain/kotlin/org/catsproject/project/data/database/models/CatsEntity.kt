package org.catsproject.project.data.database.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class CatsEntity:RealmObject {
    @PrimaryKey
    var _id:String = ""
    var url:String = ""
    var name:String = ""
    var temperament:String = ""
    var description:String = ""
    var energyLevel:Int = 0
    var intelligence:Int = 0
    var page:Int = 1
    var origin:String = ""
    var life_span:String = ""
}


