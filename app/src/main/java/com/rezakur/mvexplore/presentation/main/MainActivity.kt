package com.rezakur.mvexplore.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.rezakur.mvexplore.R
import com.rezakur.mvexplore.databinding.ActivityMainBinding
import com.rezakur.mvexplore.presentation.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var tabTitleArrays = emptyArray<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTabTitle()
        setToolbar()

        val adapter = MainViewPagerAdapter(supportFragmentManager, lifecycle)

        binding.let {
            it.viewPager.adapter = adapter
            it.viewPager.offscreenPageLimit = 1
            TabLayoutMediator(it.tabLayout, it.viewPager) { tab, position ->
                tab.text = tabTitleArrays[position]
            }.attach()
        }
    }

    private fun setTabTitle() {
        tabTitleArrays =
            arrayOf(
                getString(R.string.tab_title_movie), getString(
                    R.string.tab_title_tv_show
                )
            )
    }

    private fun setToolbar() {
        binding.toolbar.let {
            it.textTitleBar.text = getString(R.string.home_page_title)
            it.toolbar.inflateMenu(R.menu.toolbar_menu)
            it.toolbar.setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.action_favorite -> {
                        startActivity(Intent(this, FavoriteActivity::class.java))
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }
}