package com.example.infogaming.data


import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call



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

interface YouTubeApiService {
    @GET("search")
    fun searchVideos(
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 1,
        @Query("type") type: String = "video"
    ): Call<YouTubeSearchResponse>
}