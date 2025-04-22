package com.example.infogaming.activity.newsletter_s

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.infogaming.data.Article
import com.example.infogaming.databinding.ItemNewsletterBinding
import com.squareup.picasso.Picasso

class NewsletterAdapter(
    var items: List<Article>,
    val onClick: (Article) -> Unit // Usamos un Article directamente en lugar de posición
) : RecyclerView.Adapter<NewsletterAdapter.newsletter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsletter {
        val binding = ItemNewsletterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return newsletter(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: newsletter, position: Int) {
        val article = items[position]
        holder.bind(article)
        holder.itemView.setOnClickListener {
            onClick(article) // Pasamos el objeto completo
        }
    }

    class newsletter(private val binding: ItemNewsletterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.newsTitle.text = article.title
            binding.newsContent.text = article.description ?: "Sin descripción"
            Picasso.get().load(article.urlToImage).into(binding.newsImage)
            binding.newsSourceDate.text = article.publishedAt

            // Manejar el clic en el botón para abrir el artículo completo
            binding.openFullArticleBtn.setOnClickListener {
                val url = article.url
                if (!url.isNullOrEmpty()) {
                    // Creamos un intent para abrir la URL en el navegador predeterminado
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(itemView.context, intent, null) // Redirige al URL del artículo
                } else {
                    Toast.makeText(itemView.context, "URL no disponible", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


