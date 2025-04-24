package com.example.infogaming.activity.free_games

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.infogaming.R
import com.example.infogaming.data.GamesServices
import com.example.infogaming.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.infogaming.data.YouTubeApiService
import com.example.infogaming.data.YouTubeSearchResponse
import retrofit2.Call

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding // Aquí está la referencia al ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater) // Inicializamos el binding
        setContentView(binding.root)

        // Recibir el GAME_ID desde el Intent
        val gameId = intent.getIntExtra("GAME_ID", -1)

        if (gameId != -1) {
            // Buscar el juego usando el gameId
            loadGameDetails(gameId)
        }
    }

    fun getRetrofit(): GamesServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        return retrofit.create(GamesServices::class.java)
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    fun loadGameDetails(gameId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit() // Asegúrate de que el servicio sea el correcto
                val gameDetails =
                    service.getGameById(gameId) // Llamada a la API para obtener el juego completo
                val gameTitle = gameDetails.title
                searchYouTubeVideo(gameTitle)

                // Regresamos al hilo principal para actualizar la UI
                CoroutineScope(Dispatchers.Main).launch {
                    binding.titlegame.text = gameDetails.title
                    binding.descriptiongame.text = gameDetails.short_description
                    Picasso.get().load(gameDetails.thumbnail).into(binding.gameImageView)
                    binding.genreTextView.text = gameDetails.genre
                    binding.platformTextView.text = gameDetails.platform
                    binding.publisherTextView.text = gameDetails.publisher
                    binding.buttonGetGame.setOnClickListener {
                        val url = gameDetails.game_url

                        if (!url.isNullOrEmpty()) {
                            AlertDialog.Builder(this@GameDetailActivity)
                                .setTitle("Abrir juego")
                                .setMessage("¿Deseas abrir el juego en el navegador?")
                                .setPositiveButton("Sí") { _, _ ->
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    startActivity(intent)
                                }
                                .setNegativeButton("Cancelar", null)
                                .show()
                        } else {
                            val message = getString(R.string.toast_article_opened)
                            Toast.makeText(this@GameDetailActivity, message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        binding.youtubeWebView.settings.javaScriptEnabled = true
                        binding.youtubeWebView.webViewClient = WebViewClient()

                    }
                }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun searchYouTubeVideo(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(YouTubeApiService::class.java)
        val call = service.searchVideos(query, "AIzaSyAtv2lmALbUIrZEjzmduRiY4EZaohvDVFk")

        call.enqueue(object : retrofit2.Callback<YouTubeSearchResponse> {
            override fun onResponse(
                call: Call<YouTubeSearchResponse>,
                response: retrofit2.Response<YouTubeSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val videoId = response.body()?.items?.firstOrNull()?.id?.videoId
                    if (!videoId.isNullOrEmpty()) {
                        // esto es lo que abre el video dentro de la app
                        val embedUrl = "https://www.youtube.com/embed/$videoId"
                        binding.youtubeWebView.settings.javaScriptEnabled = true
                        binding.youtubeWebView.webViewClient = WebViewClient()
                        binding.youtubeWebView.loadUrl(embedUrl)
                    }
                }
            }
            override fun onFailure(
                call: Call<YouTubeSearchResponse>,
                t: Throwable
            ) {
                t.printStackTrace()
            }
        })
    }
}