package com.rezakur.mvexplore.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rezakur.mvexplore.R
import com.rezakur.mvexplore.databinding.ItemProductionCompanyBinding
import com.rezakur.mvexplore.domain.models.ProductionCompany

class ProductionCompanyAdapter  : RecyclerView.Adapter<ProductionCompanyAdapter.MainViewHolder>() {
    private var productionCompanies = ArrayList<ProductionCompany>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<ProductionCompany>?) {
        if (newList == null) return
        productionCompanies.clear()
        productionCompanies.addAll(newList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_production_company, parent, false)
        )


    override fun getItemCount(): Int = productionCompanies.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(productionCompanies[position])
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProductionCompanyBinding.bind(itemView)

        fun bind(productionCompany: ProductionCompany) {
            with(binding) {
                textTitle.text = productionCompany.name

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500${productionCompany.posterPath}")
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