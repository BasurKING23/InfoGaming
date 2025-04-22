package com.example.infogaming.activity.free_games

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.infogaming.data.GamesServices
import com.example.infogaming.databinding.ActivityDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.core.net.toUri

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

    @SuppressLint("SetTextI18n")
    fun loadGameDetails(gameId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit() // Asegúrate de que el servicio sea el correcto
                val gameDetails = service.getGameById(gameId) // Llamada a la API para obtener el juego completo

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
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = url.toUri()
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
