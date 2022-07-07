package com.bnawan.gituwu.ui.main

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bnawan.gituwu.R
import com.bnawan.gituwu.ui.adapter.ListUserAdapter
import com.bnawan.gituwu.ui.adapter.OnUserClickCallback
import com.bnawan.gituwu.databinding.ActivityMainBinding
import com.bnawan.gituwu.model.User
import com.bnawan.gituwu.ui.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val listUsers = ArrayList<User>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.header.headerTitle.text = resources.getString(R.string.home_title)
        setSupportActionBar(binding.header.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.listUsers.setHasFixedSize(true)

        viewModel.isLoading.observe(this) { isLoading ->
            onLoading(isLoading)
        }

        viewModel.responseHandler.observe(this) { response ->
            response.getContentIfNotHandled()?.let { responseHandler ->
                if (!responseHandler.status) onFailed(responseHandler.message)
            }
            if (response.peekContent().status) onSuccess()
        }

        showRecyclerList()

        viewModel.listUser.observe(this) { items ->
            listUsers.apply {
                clear()
                addAll(items)
            }
            binding.listUsers.adapter?.notifyDataSetChanged()
        }
    }

    private fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                searchLoading.visibility = View.VISIBLE
                noSearchIcon.visibility = View.GONE
                noSearchText.visibility = View.GONE
                listUsers.visibility = View.GONE
            }
        } else {
            binding.searchLoading.visibility = View.GONE
        }
    }

    private fun onSuccess() {
        binding.apply {
            noSearchIcon.visibility = View.GONE
            noSearchText.visibility = View.GONE
            listUsers.visibility = View.VISIBLE
        }
    }

    private fun onFailed(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        binding.apply {
            noSearchIcon.visibility = View.VISIBLE
            noSearchText.visibility = View.VISIBLE
            listUsers.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchUser(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.listUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.listUsers.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = ListUserAdapter(listUsers)
        binding.listUsers.adapter = listUserAdapter

        listUserAdapter.setOnUserClickCallback(object : OnUserClickCallback {
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