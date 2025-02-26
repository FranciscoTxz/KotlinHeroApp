package com.samaxz.heroapp

import com.samaxz.heroapp.dataresponse.SuperHeroDataResponse
import com.samaxz.heroapp.dataresponse.SuperHeroDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/30ffb09b14f2d45d96f9df1f103d333e/search/{name}")
    suspend fun getSuperHeroes(@Path("name") superHeroName : String) : Response<SuperHeroDataResponse>

    @GET("/api/30ffb09b14f2d45d96f9df1f103d333e/{id}")
    suspend fun getSuperHeroInformation(@Path("id") superHeroID: String) : Response<SuperHeroDetailResponse>
}