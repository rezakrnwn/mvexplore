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
import com.rezakur.core.databinding.ItemCatalogBinding
import com.rezakur.core.domain.models.Favorite
import java.util.Locale

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MainViewHolder>() {
    private var favorites = ArrayList<Favorite>()
    private var onItemClick: ((Favorite) -> Unit)? = null

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCatalogBinding.bind(itemView)

        fun bind(favorite: Favorite) {
            with(binding) {
                root.setOnClickListener {
                    onItemClick?.invoke(favorite)
                }

                textTitle.text = favorite.title
                textOverview.text = favorite.overview
                ratingBar.rating =
                    (String.format(Locale.US, "%.1f", favorite.voteAverage.toFloat())
                        .toFloat() / 10) * 5
                textRating.text =
                    String.format(Locale.US,"%.1f", favorite.voteAverage.toFloat())

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${favorite.imagePath}")
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.baseline_local_movies_24
                        )
                    )
                    .into(imageView)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Favorite>?) {
        if (newList == null) return
        favorites.clear()
        favorites.addAll(newList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (Favorite) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
    )

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.bind(favorite)
    }
}