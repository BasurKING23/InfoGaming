package com.example.infogaming.activity.free_games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.infogaming.data.GamesServices
import com.example.infogaming.databinding.FreeGamesBinding
import kotlinx.coroutines.CoroutineScope
import com.example.infogaming.data.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailsFragmentFreeGames : Fragment() {

    lateinit var binding: FreeGamesBinding        //   Cambia el nombre de la clase a ActivityDetailsBinding
    lateinit var gamefree: Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout
        binding = FreeGamesBinding.inflate(inflater, container, false)

        val id = arguments?.getInt("GAME_ID", -1) // Usa 'arguments' en lugar de 'intent'
        if (id != -1) {
            if (id != null) {
                getGameById(id)
            }
        }

        return binding.root
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
                    // Aquí puedes actualizar la UI con los datos recibidos
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
