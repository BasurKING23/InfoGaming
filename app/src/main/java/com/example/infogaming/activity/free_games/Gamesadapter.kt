package com.example.infogaming.activity.free_games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infogaming.data.Game
import com.example.infogaming.databinding.ItemGamesBinding
import com.squareup.picasso.Picasso


class GamesAdapter(var items: List<Game>, val onClick: (Any) -> Unit) : RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val binding = ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val game = items[position]
        holder.bind(game)
        holder.itemView.setOnClickListener {
//            onClick(position)
        }
    }

    class GamesViewHolder(private val binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.nameVideogameTextView.text = game.title
            Picasso.get()
                .load(game.thumbnail)
                .into(binding.gameImageView)
        }
    }
}
