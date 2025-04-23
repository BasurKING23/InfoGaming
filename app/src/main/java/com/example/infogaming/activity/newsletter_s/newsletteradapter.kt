package com.example.infogaming.activity.newsletter_s

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.infogaming.R
import com.example.infogaming.data.Article
import com.example.infogaming.databinding.ItemNewsletterBinding
import com.squareup.picasso.Picasso

class NewsletterAdapter(
    var items: List<Article>,
    val onClick: (Article) -> Unit // Usamos un Article directamente en lugar de posición
) : RecyclerView.Adapter<NewsletterAdapter.newsletter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsletter {
        val binding =
            ItemNewsletterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

            binding.openFullArticleBtn.setOnClickListener {
                val context = itemView.context
                val url = article.url

                if (!url.isNullOrEmpty()) {
                    AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.dialog_title_with_news))
                        .setMessage(context.getString(R.string.dialog_message_news))
                        .setPositiveButton("Sí") { _, _ ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                } else {
                    Toast.makeText(this.itemView.context, itemView.context.getString(R.string.toast_article_opened), Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}


