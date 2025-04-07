package com.example.infogaming.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.infogaming.R
import com.example.infogaming.data.GameServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var adapter: SuperheroAdapter
    lateinit var binding: ActivityMainBinding
    var gamelist: List<Games> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun getRetrofit(): GameServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/games")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SuperheroService::class.java)
    }
    fun getSuperheroById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                superhero = service.findSuperheroById(id)

                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}