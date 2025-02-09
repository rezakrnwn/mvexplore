package com.rezakur.mvexplore.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rezakur.core.base.BaseView
import com.rezakur.core.constant.IntentConstants
import com.rezakur.core.di.databaseModule
import com.rezakur.core.di.networkModule
import com.rezakur.core.di.repositoryModule
import com.rezakur.core.di.useCaseModule
import com.rezakur.core.ui.FavoriteAdapter
import com.rezakur.mvexplore.R
import com.rezakur.mvexplore.favorite.databinding.ActivityFavoriteBinding
import com.rezakur.mvexplore.favorite.di.viewModelModule
import com.rezakur.mvexplore.presentation.detail.DetailActivity
import com.rezakur.mvexplore.favorite.viewmodels.FavoriteIntent
import com.rezakur.mvexplore.favorite.viewmodels.FavoriteState
import com.rezakur.mvexplore.favorite.viewmodels.FavoriteStatus
import com.rezakur.mvexplore.favorite.viewmodels.FavoriteViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity(), BaseView<FavoriteState> {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(viewModelModule)
        setToolbar()
        setRecyclerView()
        observeState()
        favoriteViewModel.handleIntent(FavoriteIntent.LoadFavorites)
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.toolbar.textTitleBar.text = getString(R.string.favorite)
    }

    private fun observeState() {
        favoriteViewModel.state.onEach {
            render(it)
        }.launchIn(lifecycleScope)
    }

    private fun setRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoriteAdapter
        }

        favoriteAdapter.setOnItemClickListener { favorite ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(IntentConstants.CATALOG_ID, favorite.id)
            intent.putExtra(IntentConstants.CATALOG_TYPE_ID, favorite.type.id)
            startActivity(intent)
        }
    }

    override fun render(state: FavoriteState) {
        when(state.status) {
            FavoriteStatus.LOADING -> {
                binding.progressBar.visibility = VISIBLE
            }
            else -> {
                binding.progressBar.visibility = GONE
                favoriteAdapter.setData(state.favorites)
                state.favorites.takeIf { it.isEmpty() }?.let {
                    binding.favoriteEmptyText.visibility = VISIBLE
                } ?: run {
                    binding.favoriteEmptyText.visibility = GONE
                }
            }
        }
    }
}