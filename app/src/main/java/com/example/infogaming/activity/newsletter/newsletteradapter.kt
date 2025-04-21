package com.example.infogaming.activity.newsletter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infogaming.data.Article
import com.example.infogaming.data.NewsResponse
import com.example.infogaming.databinding.DetailsNewsletterBinding
import com.squareup.picasso.Picasso


class newsletterAdapter(
    var items: List<Article>,
    val onClick: (Int) -> Unit
) : RecyclerView.Adapter<newsletterAdapter.newsletter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsletter {
        val binding = DetailsNewsletterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return newsletter(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: newsletter, position: Int) {
        val article = items[position]
        holder.bind(article)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    class newsletter(private val binding: DetailsNewsletterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.newsTitle.text = article.title
            binding.newsDescription.text = article.description ?: "Sin descripción"
            Picasso.get()
                .load(article.urlToImage)
                .into(binding.newsImage)
        }
    }
}

