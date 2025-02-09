package com.rezakur.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rezakur.core.R
import com.rezakur.core.base.BaseDiffCallback
import com.rezakur.core.databinding.ItemProductionCompanyBinding
import com.rezakur.core.domain.models.ProductionCompany
import com.rezakur.core.extensions.loadImage

class ProductionCompanyAdapter  : RecyclerView.Adapter<ProductionCompanyAdapter.MainViewHolder>() {
    private var productionCompanies = ArrayList<ProductionCompany>()

    fun setData(newList: List<ProductionCompany>?) {
        if (newList == null) return
        val diffCallback = BaseDiffCallback(
            oldList = productionCompanies,
            newList = newList,
            areItemsSame = { old, new -> old.id == new.id },
            areContentsSame = { old, new -> old == new }
        )

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        productionCompanies.clear()
        productionCompanies.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
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
                imageView.loadImage(
                    "https://image.tmdb.org/t/p/w500${productionCompany.posterPath}",
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.baseline_local_movies_24
                    )
                )
            }
        }
    }
}