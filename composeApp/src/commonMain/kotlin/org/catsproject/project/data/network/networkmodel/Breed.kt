package org.catsproject.project.data.network.networkmodel

import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val child_friendly: Int,
    val description: String,
    val energy_level: Int,
    val intelligence: Int,
    val name: String,
    val social_needs: Int,
    val temperament: String,
    val alt_names: String? = null,
    val origin:String? = null,
    val life_span:String? = null,
)