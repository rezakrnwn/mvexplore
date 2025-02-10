package com.rezakur.mvexplore.presentation.detail

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rezakur.core.BuildConfig
import com.rezakur.mvexplore.R
import com.rezakur.core.base.BaseView
import com.rezakur.core.constant.CatalogType
import com.rezakur.core.constant.IntentConstants
import com.rezakur.core.extensions.loadImage
import com.rezakur.core.ui.ProductionCompanyAdapter
import com.rezakur.core.ui.SeasonAdapter
import com.rezakur.mvexplore.databinding.ActivityDetailBinding
import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailIntent
import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailState
import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailStatus
import com.rezakur.mvexplore.presentation.detail.viewmodels.DetailViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : ScopeActivity(), BaseView<DetailState> {
    private var catalogId = 0
    private var catalogTypeId: Int = 1
    private lateinit var binding: ActivityDetailBinding
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var productionCompanyAdapter: ProductionCompanyAdapter
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        catalogId = intent.getIntExtra(IntentConstants.CATALOG_ID, 0)
        catalogTypeId = intent.getIntExtra(IntentConstants.CATALOG_TYPE_ID, 1)

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
                binding.apply {
                    progressLayout.visibility = GONE
                    nestedScrollView.visibility = VISIBLE
                    labelProductionCompany.visibility = GONE

                    val favoriteIcon =
                        if (state.isFavorite == true) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                    iButtonFavorite.setBackgroundResource(favoriteIcon)
                }

                state.catalogDetail?.data.let { catalogDetail ->
                    binding.apply {
                        toolbar.toolbar.setBackgroundColor(getColor(R.color.colorPrimary))
                        toolbar.textTitleBar.text = catalogDetail?.title ?: ""
                        textTitle.text = catalogDetail?.title ?: ""
                        textOverview.text = catalogDetail?.overview ?: ""
                        textGenre.text = catalogDetail?.genres ?: ""
                        textReleaseDate.text = catalogDetail?.releaseDate?.let {
                            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

                            outputFormat.format(inputFormat.parse(it)!!)
                        } ?: ""
                        ratingBar.rating =
                            (String.format(
                                Locale.US,
                                "%.1f",
                                catalogDetail?.voteAverage?.toFloat() ?: 0f
                            )
                                .toFloat() / 10) * 5
                        textRating.text =
                            String.format(
                                Locale.US,
                                "%.1f",
                                catalogDetail?.voteAverage?.toFloat() ?: 0f
                            )
                        imageDetail.loadImage(
                            "https://image.tmdb.org/t/p/w500${catalogDetail?.backdropPath}",
                            ContextCompat.getDrawable(
                                applicationContext,
                                R.drawable.baseline_local_movies_24
                            )
                        )
                    }

                    catalogDetail?.seasons?.takeIf { it.isNotEmpty() }?.let {
                        binding.apply {
                            labelSeason.visibility = VISIBLE
                            rvSeason.visibility = VISIBLE
                        }
                        seasonAdapter.setData(it)
                    }

                    catalogDetail?.productionCompanies?.takeIf { it.isNotEmpty() }?.let {
                        binding.apply {
                            labelProductionCompany.visibility = VISIBLE
                            rvProductionCompany.visibility = VISIBLE
                        }
                        productionCompanyAdapter.setData(it)
                    }
                }
            }
        }
    }
}