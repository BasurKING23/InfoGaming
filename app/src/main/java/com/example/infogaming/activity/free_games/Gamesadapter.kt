package com.example.infogaming.activity.free_games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.infogaming.data.Game
import com.example.infogaming.databinding.ItemGamesBinding
import com.squareup.picasso.Picasso


class Gamesadapter(var items: List<Game>, val onClick: (Int) -> Unit) : Adapter<GamesfreeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesfreeViewHolder {
        val binding = ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesfreeViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GamesfreeViewHolder, position: Int) {
        val id = items[position]
        holder.render(id)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }
}

class GamesfreeViewHolder(val binding: ItemGamesBinding) : ViewHolder(binding.root) {

    fun render(videogames: Game) {

        binding.nameVideogameTextView.text = videogames.title

        Picasso.get()
            .load(videogames.thumbnail)
            .into(binding.gameImageView)
    }
}