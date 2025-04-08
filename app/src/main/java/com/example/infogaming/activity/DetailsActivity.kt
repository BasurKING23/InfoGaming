package com.example.infogaming.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.infogaming.data.Game
import com.example.infogaming.data.GamesServices
import com.example.infogaming.databinding.ActivityDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding        //   Cambia el nombre de la clase a ActivityDetailsBinding
    lateinit var gamefree: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding =
            ActivityDetailsBinding.inflate(layoutInflater)  // Cambia el nombre de la clase a ActivityDetailsBinding
        setContentView(binding.root)

        val id = intent.getIntExtra("GAME_ID", -1)
        if (id != -1) {
            getGameById(id)
        }

    }

    fun getRetrofit(): GamesServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GamesServices::class.java)
    }


    fun getGameById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                gamefree = service.getGameById(id)

                Log.i("API", gamefree.toString())
                CoroutineScope(Dispatchers.Main).launch {
//                        loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}