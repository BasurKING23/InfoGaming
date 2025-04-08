package com.example.infogaming.data


import retrofit2.http.GET
import retrofit2.http.Query


interface GamesServices {
    @GET("games")
    suspend fun getAllGames(): List<Game>

    @GET("game")
    suspend fun getGameById(@Query("id") id: Int): Game
}

//game?id=452