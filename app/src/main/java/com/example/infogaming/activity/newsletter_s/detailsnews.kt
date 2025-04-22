package com.example.infogaming.activity.newsletter_s

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infogaming.data.Article
import com.example.infogaming.data.newsServices
import com.example.infogaming.databinding.NewslettersBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Newsletter : Fragment() {

    private lateinit var binding: NewslettersBinding
    private lateinit var adapter: NewsletterAdapter
    private var articleList = listOf<Article>()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewslettersBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // Llama a loadGames() para obtener los datos
        loadNews()

        return binding.root
    }

    fun getRetrofit(): newsServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(newsServices::class.java)
    }

    fun loadNews() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressIndicator.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                val result = service.getAllNews() // Esto debería devolver una respuesta con artículos
                articleList = Article //

                // Regresamos al hilo principal
                CoroutineScope(Dispatchers.Main).launch {
                    binding.progressIndicator.visibility = View.GONE

                    // Verifica si la lista de artículos no está vacía
                    adapter = NewsletterAdapter(articleList) { position ->
                        val article = articleList[position]
                        // Si necesitas realizar algo con el artículo, puedes hacerlo aquí
                    }

                    binding.recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Newsletter", "Error loading news", e)
                CoroutineScope(Dispatchers.Main).launch {
                    binding.progressIndicator.visibility = View.GONE
                }
            }

        }
    }
}

