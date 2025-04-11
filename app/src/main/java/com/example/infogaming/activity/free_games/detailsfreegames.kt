package com.example.infogaming.activity.free_games
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infogaming.data.Game
import com.example.infogaming.data.GamesServices
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

            // Configura el RecyclerView
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
            // Hacemos la llamada a la API en un hilo de fondo
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val service = getRetrofit()
                    val result = service.getAllGames()

                    gamelist = result

                    // Regresamos al hilo principal para actualizar la UI
                    CoroutineScope(Dispatchers.Main).launch {
                        // Inicializamos el adapter después de obtener los datos
                        adapter = GamesAdapter(gamelist) { position ->
                            val game = gamelist[position as Int]

                            // Usamos requireActivity() para el contexto en Fragment
                            val intent = Intent(requireActivity(), FreeGames::class.java)
                            intent.putExtra("GAME_ID", game.id)
                            startActivity(intent)
                        }

                        // Actualizamos el RecyclerView con el adapter
                        binding.recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


