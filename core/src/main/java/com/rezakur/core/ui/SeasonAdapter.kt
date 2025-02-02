package com.rezakur.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rezakur.core.R
import com.rezakur.core.databinding.ItemSeasonBinding
import com.rezakur.core.domain.models.Season

class SeasonAdapter : RecyclerView.Adapter<SeasonAdapter.MainViewHolder>() {
    private var seasonList = ArrayList<Season>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Season>?) {
        if (newList == null) return
        seasonList.clear()
        seasonList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_season, parent, false)
        )


    override fun getItemCount(): Int = seasonList.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(seasonList[position])
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSeasonBinding.bind(itemView)

        fun bind(season: Season) {
            with(binding) {
                textTitle.text = season.title

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${season.posterPath}")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.baseline_local_movies_24
                        )
                    )
                    .into(imageView)
            }
        }
    }
}