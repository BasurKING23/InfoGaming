package com.example.infogaming.activity.new

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infogaming.activity.newsletter_s.Newsletter
import com.example.infogaming.activity.newsletter_s.NewsletterAdapter
import com.example.infogaming.data.Article
import com.example.infogaming.data.newsServices
import com.example.infogaming.databinding.NewslettersBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Newsletters : Fragment() {
    private lateinit var binding: NewslettersBinding
    private lateinit var adapter: NewsletterAdapter
    private var articlelist = listOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewslettersBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // Llama a loadGames() para obtener los datos
        loadGames()

        return binding.root
    }

    fun getRetrofit(): newsServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(newsServices::class.java)
    }

    fun loadGames() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progressIndicator.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                val result = service.getAllNews()
                articlelist = result.articles

                CoroutineScope(Dispatchers.Main).launch {
                    binding.progressIndicator.visibility = View.GONE

                    // Cambié la lambda a recibir el objeto Article
                    adapter = NewsletterAdapter(articlelist) { article ->
                        val intent = Intent(requireActivity(), Newsletter::class.java)
                        intent.putExtra("NEWS_TITLE", article.title)
                        startActivity(intent)
                    }

                    binding.recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                CoroutineScope(Dispatchers.Main).launch {
                    binding.progressIndicator.visibility = View.GONE
                }
            }
        }
    }
}



