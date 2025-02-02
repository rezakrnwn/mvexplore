package com.rezakur.mvexplore.presentation.movie

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
import com.rezakur.mvexplore.BuildConfig
import com.rezakur.mvexplore.core.base.BaseView
import com.rezakur.mvexplore.core.constant.CatalogType
import com.rezakur.mvexplore.core.ui.CatalogAdapter
import com.rezakur.mvexplore.databinding.FragmentMovieBinding
import com.rezakur.mvexplore.domain.models.Catalog
import com.rezakur.mvexplore.presentation.detail.DetailActivity
import com.rezakur.mvexplore.presentation.movie.viewmodels.MovieIntent
import com.rezakur.mvexplore.presentation.movie.viewmodels.MovieState
import com.rezakur.mvexplore.presentation.movie.viewmodels.MovieStatus
import com.rezakur.mvexplore.presentation.movie.viewmodels.MovieViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment

class MovieFragment : ScopeFragment(), BaseView<MovieState> {
    private lateinit var binding: FragmentMovieBinding
    private lateinit var catalogAdapter: CatalogAdapter
    private val movieViewModel: MovieViewModel by inject()
    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeState()
        movieViewModel.handleIntent(
            MovieIntent.LoadMovies(
                apiKey = BuildConfig.API_KEY, page = currentPage
            )
        )
    }

    private fun observeState() {
        movieViewModel.state.onEach {
            render(it)
        }.launchIn(lifecycleScope)
    }

    private fun setRecyclerView() {
        catalogAdapter = CatalogAdapter()
        binding.rvMovie.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = catalogAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    if (!isLoading && layoutManager.findLastVisibleItemPosition() == catalogAdapter.itemCount - 1) {
                        currentPage++
                        movieViewModel.handleIntent(
                            MovieIntent.LoadMovies(
                                apiKey = BuildConfig.API_KEY, page = currentPage
                            )
                        )
                    }
                }
            })
        }

        catalogAdapter.setOnItemClickListener { catalog: Catalog ->
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("catalog_id", catalog.id)
            intent.putExtra("catalog_type_id", CatalogType.MOVIE.id)
            startActivity(intent)
        }
    }

    override fun render(state: MovieState) {
        when (state.status) {
            MovieStatus.LOADING -> {
                if (state.movies.isEmpty()) {
                    isLoading = true
                    binding.apply {
                        progressCircular.visibility = VISIBLE
                        rvMovie.visibility = GONE
                    }
                }
            }

            MovieStatus.ERROR -> {
                isLoading = false
                binding.progressCircular.visibility = GONE
                binding.textError.text = state.message
            }

            else -> {
                isLoading = false
                binding.apply {
                    progressCircular.visibility = GONE
                    rvMovie.visibility = VISIBLE
                }
                catalogAdapter.setData(state.movies, !state.hasReachedMax)
            }
        }
    }
}