package com.rezakur.mvexplore.presentation.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rezakur.mvexplore.R
import com.rezakur.core.base.BaseView
import com.rezakur.core.ui.FavoriteAdapter
import com.rezakur.mvexplore.databinding.ActivityFavoriteBinding
import com.rezakur.mvexplore.presentation.detail.DetailActivity
import com.rezakur.mvexplore.presentation.favorite.viewmodels.FavoriteIntent
import com.rezakur.mvexplore.presentation.favorite.viewmodels.FavoriteState
import com.rezakur.mvexplore.presentation.favorite.viewmodels.FavoriteStatus
import com.rezakur.mvexplore.presentation.favorite.viewmodels.FavoriteViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity(), BaseView<FavoriteState> {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            intent.putExtra("catalog_id", favorite.id)
            intent.putExtra("catalog_type_id", favorite.type.id)
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
            }
        }
    }
}