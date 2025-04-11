import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infogaming.R
import com.example.infogaming.activity.free_games.DetailsFragmentFreeGames
import com.example.infogaming.activity.free_games.Gamesadapter
import com.example.infogaming.data.Game
import com.example.infogaming.data.GamesServices
import com.example.infogaming.databinding.FreeGamesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FreeGamesFragment : Fragment() {

    lateinit var adapter: Gamesadapter
    lateinit var binding: FreeGamesBinding
    var gamelist: List<Game> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        binding = FreeGamesBinding.inflate(inflater, container, false)

        // Configurar RecyclerView
        adapter = Gamesadapter(gamelist) { position ->
            val game = gamelist[position]

            // Manejar el clic en el ítem, aquí se puede pasar el id del juego al fragmento o a otra activity
            val intent = Intent(activity, DetailsFragmentFreeGames::class.java) // o puedes pasar al mismo Fragment
            intent.putExtra("GAME_ID", game.id)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // Cargar los juegos desde la API
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                val result = service.getAllGames()

                gamelist = result

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.items = gamelist
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}
