package com.bnawan.gituwu

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bnawan.gituwu.adapter.ListUserAdapter
import com.bnawan.gituwu.adapter.OnUserClickCallback
import com.bnawan.gituwu.databinding.ActivityMainBinding
import com.bnawan.gituwu.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listUsers = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.header.headerTitle.text = resources.getString(R.string.home_title)
        binding.listUsers.setHasFixedSize(true)

        listUsers.addAll(getListUsers)
        showRecyclerList()
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
            override fun onItemClicked(user: User) {
                moveToDetail(user)
            }
        })
    }

    private fun moveToDetail(user: User) {
        val detailActivity = Intent(this@MainActivity, DetailActivity::class.java)
        detailActivity.putExtra(DetailActivity.DATA_USER, user)
        startActivity(detailActivity)
    }

    private val getListUsers: ArrayList<User>
        get() {
            val name = resources.getStringArray(R.array.name)
            val username = resources.getStringArray(R.array.username)
            val avatar = resources.obtainTypedArray(R.array.avatar)
            val follower = resources.getStringArray(R.array.followers)
            val following = resources.getStringArray(R.array.following)
            val location = resources.getStringArray(R.array.location)
            val repository = resources.getStringArray(R.array.repository)
            val company = resources.getStringArray(R.array.company)

            val listTemp = ArrayList<User>()
            for (position in name.indices) {
                val user = User(
                    name[position],
                    username[position],
                    avatar.getResourceId(position, -1),
                    follower[position].toLong(),
                    following[position].toLong(),
                    location[position],
                    repository[position].toLong(),
                    company[position]
                )
                listTemp.add(user)
            }
            avatar.recycle()
            return listTemp
        }
}