package com.samaxz.heroapp.dataresponse

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superHeroes: List<SuperHeroItemResponse>
)

data class SuperHeroItemResponse(
    @SerializedName("id") val superHeroID: String,
    @SerializedName("name") val superHeroName: String,
    @SerializedName("image") val image: SuperHeroImage
)

data class SuperHeroImage(
    @SerializedName("url") val url: String
)