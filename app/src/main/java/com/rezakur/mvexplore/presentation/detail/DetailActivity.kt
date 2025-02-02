package com.rezakur.mvexplore.presentation.detail

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.rezakur.mvexplore.BuildConfig
import com.rezakur.mvexplore.R
import com.rezakur.mvexplore.core.base.BaseView
import com.rezakur.mvexplore.core.constant.CatalogType
import com.rezakur.mvexplore.core.ui.ProductionCompanyAdapter
import com.rezakur.mvexplore.core.ui.SeasonAdapter
import com.rezakur.mvexplore.databinding.ActivityDetailBinding
import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailIntent
import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailState
import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailStatus
import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : ScopeActivity(), BaseView<DetailState> {
    private var catalogId = 0
    private var catalogTypeId: Int = 1
    private var isFromFavorite = false
    private lateinit var binding: ActivityDetailBinding
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var productionCompanyAdapter: ProductionCompanyAdapter
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        catalogId = intent.getIntExtra("catalog_id", 0)
        catalogTypeId = intent.getIntExtra("catalog_type_id", 1)
        isFromFavorite = intent.getBooleanExtra("is_from_favorite", false)

        setFavoriteStatus()
        setToolbar()
        setRvSeason()
        setRvProductionCompany()
        observeState()

        if (catalogTypeId == CatalogType.TV_SHOW.id) {
            detailViewModel.handleIntent(
                DetailIntent.LoadTvShowDetail(
                    apiKey = BuildConfig.API_KEY,
                    id = catalogId
                )
            )
        } else {
            detailViewModel.handleIntent(
                DetailIntent.LoadMovieDetail(
                    apiKey = BuildConfig.API_KEY,
                    id = catalogId
                )
            )
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setFavoriteStatus() {
        binding.iButtonFavorite.setOnClickListener {
            if (detailViewModel.state.value.isFavorite == true) {
                detailViewModel.handleIntent(DetailIntent.RemoveFavorite)
            } else {
                detailViewModel.handleIntent(
                    DetailIntent.AddFavorite(
                        catalogTypeId = catalogTypeId,
                    )
                )
            }
        }
    }

    private fun observeState() {
        detailViewModel.state.onEach {
            render(it)
        }.launchIn(lifecycleScope)
    }

    private fun setRvSeason() {
        seasonAdapter = SeasonAdapter()
        binding.rvSeason.apply {
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = seasonAdapter
        }
    }

    private fun setRvProductionCompany() {
        productionCompanyAdapter = ProductionCompanyAdapter()
        binding.rvProductionCompany.apply {
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = productionCompanyAdapter
        }
    }

    override fun render(state: DetailState) {
        when (state.status) {
            DetailStatus.LOADING -> {
                binding.progressLayout.visibility = VISIBLE
            }

            DetailStatus.ERROR -> {
                binding.progressLayout.visibility = GONE
            }

            else -> {
                binding.progressLayout.visibility = GONE
                binding.nestedScrollView.visibility = VISIBLE
                state.catalogDetail?.data.let { catalogDetail ->
                    binding.toolbar.textTitleBar.text = catalogDetail?.title ?: ""
                    binding.textTitle.text = catalogDetail?.title ?: ""
                    binding.textOverview.text = catalogDetail?.overview ?: ""
                    binding.textGenre.text = catalogDetail?.genres ?: ""
                    binding.textReleaseDate.text = catalogDetail?.releaseDate?.let {
                        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

                        outputFormat.format(inputFormat.parse(it)!!)
                    } ?: ""
                    binding.ratingBar.rating =
                        (String.format(
                            Locale.US,
                            "%.1f",
                            catalogDetail?.voteAverage?.toFloat() ?: 0f
                        )
                            .toFloat() / 10) * 5
                    binding.textRating.text =
                        String.format(
                            Locale.US,
                            "%.1f",
                            catalogDetail?.voteAverage?.toFloat() ?: 0f
                        )


                    Glide.with(applicationContext)
                        .load("https://image.tmdb.org/t/p/w500${catalogDetail?.backdropPath}")
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(
                            ContextCompat.getDrawable(
                                this@DetailActivity,
                                R.drawable.baseline_local_movies_24
                            )
                        )
                        .into(binding.imageDetail)

                    catalogDetail?.seasons?.takeIf { it.isNotEmpty() }?.let {
                        binding.apply {
                            labelSeason.visibility = VISIBLE
                            rvSeason.visibility = VISIBLE
                        }
                        seasonAdapter.setData(it)
                    }

                    binding.labelProductionCompany.visibility = GONE
                    catalogDetail?.productionCompanies?.takeIf { it.isNotEmpty() }?.let {
                        binding.apply {
                            labelProductionCompany.visibility = VISIBLE
                            rvProductionCompany.visibility = VISIBLE
                        }
                        productionCompanyAdapter.setData(it)
                    }
                }

                if (state.isFavorite == true) {
                    binding.iButtonFavorite.setBackgroundResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.iButtonFavorite.setBackgroundResource(R.drawable.baseline_favorite_border_24)
                }
            }
        }
    }
}