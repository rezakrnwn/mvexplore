package com.rezakur.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rezakur.core.R
import com.rezakur.core.base.BaseDiffCallback
import com.rezakur.core.databinding.ItemCatalogBinding
import com.rezakur.core.domain.models.Catalog
import com.rezakur.core.extensions.loadImage
import java.util.Locale

class CatalogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val catalogList = mutableListOf<Catalog>()
    private var isLoading = false
    private var onItemClick: ((Catalog) -> Unit)? = null

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCatalogBinding.bind(itemView)

        fun bind(catalog: Catalog) {
            with(binding) {
                root.setOnClickListener {
                    onItemClick?.invoke(catalog)
                }

                textTitle.text = catalog.title
                textOverview.text = catalog.overview
                ratingBar.rating =
                    (String.format(Locale.US, "%.1f", (catalog.voteAverage ?: 0.0).toFloat())
                        .toFloat() / 10) * 5
                textRating.text =
                    String.format(Locale.US, "%.1f", (catalog.voteAverage ?: 0.0).toFloat())
                imageView.loadImage(
                    "https://image.tmdb.org/t/p/w500${catalog.imagePath}",
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.baseline_local_movies_24
                    )
                )
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setData(newList: List<Catalog>, isLoading: Boolean) {
        this.isLoading = isLoading
        val diffCallback = BaseDiffCallback(
            oldList = catalogList,
            newList = newList,
            areItemsSame = { old, new -> old.id == new.id },
            areContentsSame = { old, new -> old == new }
        )

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        catalogList.clear()
        catalogList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickListener(listener: (Catalog) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_loading) {
            LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            )

        } else {
            MainViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MainViewHolder) {
            holder.bind(catalog = catalogList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= catalogList.size && isLoading) R.layout.item_loading else R.layout.item_catalog
    }

    override fun getItemCount(): Int = catalogList.size + if (isLoading) 1 else 0
}