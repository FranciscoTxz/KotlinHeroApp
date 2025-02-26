package com.samaxz.heroapp.dataresponse

import com.google.gson.annotations.SerializedName

data class SuperHeroDetailResponse(
    @SerializedName("response") val response: String,
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerstats: PowerStatsResponse,
    @SerializedName("image") val image: SuperHeroImageDetail,
    @SerializedName("biography") val biography: SuperHeroBiography,
    @SerializedName("work") val work: SuperHeroWork
)

data class PowerStatsResponse(
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String,
)

data class SuperHeroImageDetail(
    @SerializedName("url") val url: String
)

data class SuperHeroWork(
    @SerializedName("occupation") val occupation: String,
    @SerializedName("base") val base: String
)

data class SuperHeroBiography(
    @SerializedName("full-name") val full_name: String,
    @SerializedName("alter-egos") val alter_egos: String,
    @SerializedName("place-of-birth") val place_of_birth: String,
    @SerializedName("first-appearance") val first_appearance: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("alignment") val alignment: String,
)