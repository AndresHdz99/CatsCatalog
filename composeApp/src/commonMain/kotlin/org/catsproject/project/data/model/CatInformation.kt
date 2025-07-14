package org.catsproject.project.data.model

data class CatInformation(
    var url:String,
    var name:String,
    var information:String,
    var energyLevel:Float,
    var intelligence:Float,
    var favorite:Boolean,
    var origin :String,
    val life_span:String
)
