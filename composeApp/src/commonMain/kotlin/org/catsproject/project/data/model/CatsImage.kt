package org.catsproject.project.data.model


data class CatsImage(
    val id:String,
    val url:String,
    val name:String,
    val temperament:String,
    val page:Int,
    val favorite:Boolean = false
)
