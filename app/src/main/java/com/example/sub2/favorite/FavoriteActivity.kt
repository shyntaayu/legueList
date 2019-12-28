package com.example.sub2.favorite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sub2.R
import com.example.sub2.R.id.*
import com.example.sub2.favorite.next.NextEventFavoriteFragment
import com.example.sub2.favorite.past.PastEventFavoriteFragment
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                teams -> {
                    loadPastFavoritesFragment(savedInstanceState)
                }
                favorites -> {
                    loadNextFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = teams
    }

    private fun loadNextFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, NextEventFavoriteFragment(), NextEventFavoriteFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadPastFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_container,
                    PastEventFavoriteFragment(),
                    PastEventFavoriteFragment::class.java.simpleName
                )
                .commit()
        }
    }
}
