package com.example.infogaming.data

import com.example.infogaming.data.GamesFree
import com.example.infogaming.data.SuperheroResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GameServices {

    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String): SuperheroResponse

    @GET("{game-id}")
    suspend fun findSuperheroById(@Path("superhero-id") id: String): Superhero

}