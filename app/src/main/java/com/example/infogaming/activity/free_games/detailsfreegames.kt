package com.example.infogaming.activity.free_games

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infogaming.GameDetailActivity
import com.example.infogaming.R
import com.example.infogaming.data.Game
import com.example.infogaming.data.GamesServices
import com.example.infogaming.databinding.ActivityDetailsBinding
import com.example.infogaming.databinding.FreeGamesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FreeGames : Fragment() {
    private lateinit var binding: FreeGamesBinding
    private lateinit var adapter: GamesAdapter
    private var gamelist = listOf<Game>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FreeGamesBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

            // Llama a loadGames() para obtener los datos
            loadGames()

        return binding.root
    }

    fun getRetrofit(): GamesServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(GamesServices::class.java)
    }

    fun loadGames() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressIndicator.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                val result = service.getAllGames()
                Log.d("FreeGames", "Games loaded: ${result.size}")
                gamelist = result


                // Regresamos al hilo principal
                CoroutineScope(Dispatchers.Main).launch {
                    binding.progressIndicator.visibility = View.GONE

                    // Verifica si la lista de juegos no está vacía
                    adapter = GamesAdapter(gamelist) { position ->
                        val game = gamelist[position as Int]
                    }

                    adapter = GamesAdapter(gamelist) { selectedGame ->
                        val game = gamelist[selectedGame as Int]
                        val intent = Intent(requireActivity(), GameDetailActivity::class.java)
                        intent.putExtra("GAME_ID", game.id)
                        startActivity(intent)
                    }
                    binding.recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()


                    Log.d("FreeGames", "Adapter set with ${gamelist.size} items.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("FreeGames", "Error loading games", e)
                CoroutineScope(Dispatchers.Main).launch {
                    binding.progressIndicator.visibility = View.GONE
                }
            }
        }
    }
}