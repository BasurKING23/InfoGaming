package com.example.infogaming.data


import retrofit2.http.GET
import retrofit2.http.Query


interface GamesServices {
    @GET("games")
    suspend fun getAllGames(): List<Game>

    @GET("game")
    suspend fun getGameById(@Query("id") id: Int): Game
}


interface  newsServices {
    @GET("everything?q=games")
    suspend fun getAllNews(
        @Query("apiKey") apiKey: String = "8de0f332e8c44a6a8b4927315f12742a"
    ): NewsResponse
}