package com.bnawan.gituwu.ui.favorite

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bnawan.gituwu.R
import com.bnawan.gituwu.data.Result
import com.bnawan.gituwu.databinding.ActivityFavoriteBinding
import com.bnawan.gituwu.ui.adapter.ListUserAdapter
import com.bnawan.gituwu.ui.adapter.OnUserClickCallback
import com.bnawan.gituwu.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: ListUserAdapter

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
        binding.listFavorite.setHasFixedSize(true)
        showRecyclerList()

        val viewModelFactory = FavoriteViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.listFavorite.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.listFavorite.layoutManager = LinearLayoutManager(this)
        }
        adapter = ListUserAdapter()
        binding.listFavorite.adapter = adapter

        adapter.setOnUserClickCallback(object : OnUserClickCallback {
            override fun onItemClicked(username: String) {
                moveToDetail(username)
            }
        })
    }

    private fun moveToDetail(username: String) {
        val detailActivity = Intent(this, DetailActivity::class.java)
        detailActivity.putExtra(DetailActivity.DATA_USER, username)
        startActivity(detailActivity)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.getListFavoriteUser().observe(this@FavoriteActivity) { result ->
                result?.let {
                    when (result) {
                        is Result.Loading -> onLoading()
                        is Result.Error -> onFailed(result.error)
                        is Result.Success -> {
                            onSuccess()
                            adapter.setListUser(result.data)
                        }
                    }
                }
            }
        }
    }

    private fun onLoading() {
        binding.apply {
            favoriteLoading.visibility = View.VISIBLE
            noFavoriteIcon.visibility = View.GONE
            noFavoriteText.visibility = View.GONE
            listFavorite.visibility = View.GONE
        }
    }

    private fun onSuccess() {
        binding.apply {
            favoriteLoading.visibility = View.GONE
            noFavoriteIcon.visibility = View.GONE
            noFavoriteText.visibility = View.GONE
            listFavorite.visibility = View.VISIBLE
        }
    }

    private fun onFailed(message: String) {
        binding.apply {
            favoriteLoading.visibility = View.GONE
            noFavoriteIcon.visibility = View.VISIBLE
            noFavoriteText.visibility = View.VISIBLE
            noFavoriteText.text = message
            listFavorite.visibility = View.GONE
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