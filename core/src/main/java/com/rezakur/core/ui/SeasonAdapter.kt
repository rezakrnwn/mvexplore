package com.rezakur.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rezakur.core.R
import com.rezakur.core.base.BaseDiffCallback
import com.rezakur.core.databinding.ItemSeasonBinding
import com.rezakur.core.domain.models.Season
import com.rezakur.core.extensions.loadImage

class SeasonAdapter : RecyclerView.Adapter<SeasonAdapter.MainViewHolder>() {
    private var seasonList = ArrayList<Season>()

    fun setData(newList: List<Season>?) {
        if (newList == null) return
        val diffCallback = BaseDiffCallback(
            oldList = seasonList,
            newList = newList,
            areItemsSame = { old, new -> old.id == new.id },
            areContentsSame = { old, new -> old == new }
        )

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        seasonList.clear()
        seasonList.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
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
                imageView.loadImage(
                    "https://image.tmdb.org/t/p/w500${season.posterPath}",
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.baseline_local_movies_24
                    )
                )
            }
        }
    }
}