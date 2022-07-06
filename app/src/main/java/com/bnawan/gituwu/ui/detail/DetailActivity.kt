package com.bnawan.gituwu.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bnawan.gituwu.R
import com.bnawan.gituwu.databinding.ActivityDetailBinding
import com.bnawan.gituwu.model.User
import com.bnawan.gituwu.ui.adapter.FollowTabAdapter
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailHeader.headerTitle.text = resources.getString(R.string.detail_title)
        setSupportActionBar(binding.detailHeader.toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24)
            it.setDisplayShowTitleEnabled(false)
        }
        username = intent.getStringExtra(DATA_USER) ?: ""

        setFollowTab()

        val viewModelFactory = DetailViewModelFactory(username)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        viewModel.isLoading.observe(this) { isLoading ->
            onLoading(isLoading)
        }

        viewModel.user.observe(this) { user ->
            setDetailInformation(user)
        }

        viewModel.responseHandler.observe(this){response->
            response.getContentIfNotHandled()?.let{responseHandler->
                when (responseHandler.status) {
                    true -> onSuccess()
                    false-> onFailed(responseHandler.message)
                }
            }
        }
    }

    private fun onLoading(isLoading: Boolean) {
        if(isLoading){
            binding.apply {
                detailLoading.visibility = View.VISIBLE
                detailContent.root.visibility = View.INVISIBLE
            }
        }else{
            binding.detailLoading.visibility = View.GONE
        }
    }

    private fun onSuccess() {
        binding.detailContent.root.visibility = View.VISIBLE
    }

    private fun onFailed(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        binding.detailContent.root.visibility = View.VISIBLE
    }

    private fun setFollowTab() {
        val followTabAdapter = FollowTabAdapter(this)
        followTabAdapter.username = username
        binding.apply {
            detailViewPager.adapter = followTabAdapter
            TabLayoutMediator(detailTab, detailViewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
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

    private fun setDetailInformation(user: User) {
        binding.detailContent.apply {
            detailImg.loadImage(user.avatarUrl)
            detailName.text = user.name
            detailUsername.text = user.username
            detailFollower.text = user.followers.toString()
            detailFollowing.text = user.following.toString()
            detailRepo.text = user.publicRepos.toString()
            detailCompany.text = user.company ?: resources.getString(R.string.strip)
            detailLocation.text = user.location ?: resources.getString(R.string.strip)
        }
    }

    private fun ImageView.loadImage(src: String?) {
        Glide.with(this.context).load(src)
            .circleCrop()
            .into(this)
    }

    companion object {
        const val DATA_USER = "data_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}