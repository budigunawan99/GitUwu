package com.bnawan.gituwu.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bnawan.gituwu.R
import com.bnawan.gituwu.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoriteHeader.headerTitle.text = resources.getString(R.string.favorite)
        setSupportActionBar(binding.favoriteHeader.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
            it.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }
}