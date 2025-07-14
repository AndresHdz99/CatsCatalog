package org.catsproject.project.data.database.models

data class CatResulEntity (
    var _id:String,
    var url:String,
    var name:String,
    var temperament:String,
    var description:String,
    var energyLevel:Int,
    var intelligence:Int,
    var page:Int,
    var favorite:Boolean = true,
    var origin:String,
    val life_span:String
)