package com.bnawan.gituwu.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bnawan.gituwu.R
import com.bnawan.gituwu.ui.adapter.ListUserAdapter
import com.bnawan.gituwu.ui.adapter.OnUserClickCallback
import com.bnawan.gituwu.databinding.ActivityMainBinding
import com.bnawan.gituwu.ui.detail.DetailActivity
import com.bnawan.gituwu.ui.favorite.FavoriteActivity
import com.bnawan.gituwu.ui.setting.SettingActivity
import com.google.android.material.snackbar.Snackbar
import com.bnawan.gituwu.data.Result

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = MainViewModelFactory.getInstance(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.header.headerTitle.text = resources.getString(R.string.home_title)
        setSupportActionBar(binding.header.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.listUsers.setHasFixedSize(true)
        showRecyclerList()
    }

    private fun onLoading() {
        binding.apply {
            searchLoading.visibility = View.VISIBLE
            noSearchIcon.visibility = View.GONE
            noSearchText.visibility = View.GONE
            listUsers.visibility = View.GONE
        }
    }

    private fun onSuccess() {
        binding.apply {
            searchLoading.visibility = View.GONE
            noSearchIcon.visibility = View.GONE
            noSearchText.visibility = View.GONE
            listUsers.visibility = View.VISIBLE
        }
    }

    private fun onFailed(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        binding.apply {
            searchLoading.visibility = View.GONE
            noSearchIcon.visibility = View.VISIBLE
            noSearchText.visibility = View.VISIBLE
            listUsers.visibility = View.GONE
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menu is MenuBuilder) menu.setOptionalIconsVisible(true)

        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchUser(query).observe(this@MainActivity) { result ->
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
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.listUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.listUsers.layoutManager = LinearLayoutManager(this)
        }
        adapter = ListUserAdapter()
        binding.listUsers.adapter = adapter

        adapter.setOnUserClickCallback(object : OnUserClickCallback {
            override fun onItemClicked(username: String) {
                moveToDetail(username)
            }
        })
    }

    private fun moveToDetail(username: String) {
        val detailActivity = Intent(this@MainActivity, DetailActivity::class.java)
        detailActivity.putExtra(DetailActivity.DATA_USER, username)
        startActivity(detailActivity)
    }
}