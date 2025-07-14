package org.catsproject.project.data.network.networkmodel

import kotlinx.serialization.Serializable

@Serializable
data class CatsModelServicesItem(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)