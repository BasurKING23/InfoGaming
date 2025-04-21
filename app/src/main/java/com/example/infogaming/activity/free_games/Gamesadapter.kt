package com.example.infogaming.activity.free_games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infogaming.data.Game
import com.example.infogaming.databinding.ItemGamesBinding
import com.squareup.picasso.Picasso


class GamesAdapter(var items: List<Game>, val onClick: (Any) -> Unit) : RecyclerView.Adapter<GamesAdapter.newsletter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsletter {
        val binding = ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return newsletter(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: newsletter, position: Int) {
        val game = items[position]
        holder.bind(game)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    class newsletter(private val binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.nameVideogameTextView.text = game.title
            Picasso.get()
                .load(game.thumbnail)
                .into(binding.gameImageView)
        }
    }

}
