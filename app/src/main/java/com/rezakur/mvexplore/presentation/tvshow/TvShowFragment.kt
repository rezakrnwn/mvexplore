package com.rezakur.mvexplore.presentation.tvshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rezakur.core.BuildConfig
import com.rezakur.core.base.BaseView
import com.rezakur.core.constant.CatalogType
import com.rezakur.core.constant.IntentConstants
import com.rezakur.core.domain.models.Catalog
import com.rezakur.core.ui.CatalogAdapter
import com.rezakur.mvexplore.databinding.FragmentTvShowBinding
import com.rezakur.mvexplore.presentation.detail.DetailActivity
import com.rezakur.mvexplore.presentation.tvshow.viewmodels.TvShowIntent
import com.rezakur.mvexplore.presentation.tvshow.viewmodels.TvShowState
import com.rezakur.mvexplore.presentation.tvshow.viewmodels.TvShowStatus
import com.rezakur.mvexplore.presentation.tvshow.viewmodels.TvShowViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment

class TvShowFragment : ScopeFragment(), BaseView<TvShowState> {
    private lateinit var binding: FragmentTvShowBinding
    private lateinit var catalogAdapter: CatalogAdapter
    private val tvShowViewModel: TvShowViewModel by inject()
    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeState()
        tvShowViewModel.handleIntent(
            TvShowIntent.LoadTvShows(
                apiKey = BuildConfig.API_KEY, page = currentPage
            )
        )
    }

    private fun observeState() {
        tvShowViewModel.state.onEach {
            render(it)
        }.launchIn(lifecycleScope)
    }

    private fun setRecyclerView() {
        catalogAdapter = CatalogAdapter()
        binding.rvTvShow.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = catalogAdapter
        }

        catalogAdapter.setOnItemClickListener { catalog: Catalog ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IntentConstants.CATALOG_ID, catalog.id)
            intent.putExtra(IntentConstants.CATALOG_TYPE_ID, CatalogType.TV_SHOW.id)
            startActivity(intent)
        }

        binding.rvTvShow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && layoutManager.findLastVisibleItemPosition() == catalogAdapter.itemCount - 1) {
                    currentPage++
                    tvShowViewModel.handleIntent(
                        TvShowIntent.LoadTvShows(
                            apiKey = BuildConfig.API_KEY, page = currentPage
                        )
                    )
                }
            }
        })
    }

    override fun render(state: TvShowState) {
        when (state.status) {
            TvShowStatus.LOADING -> {
                if (state.tvShows.isEmpty()) {
                    isLoading = true
                    binding.apply {
                        progressCircular.visibility = VISIBLE
                        rvTvShow.visibility = GONE
                    }
                }
            }

            TvShowStatus.ERROR -> {
                isLoading = false
                binding.progressCircular.visibility = GONE
                binding.textError.text = state.message
            }

            else -> {
                isLoading = false
                binding.apply {
                    progressCircular.visibility = GONE
                    rvTvShow.visibility = VISIBLE
                }
                catalogAdapter.setData(state.tvShows, !state.hasReachedMax)
            }
        }
    }
}