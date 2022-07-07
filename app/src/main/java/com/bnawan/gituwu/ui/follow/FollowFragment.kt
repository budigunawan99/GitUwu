package com.bnawan.gituwu.ui.follow

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bnawan.gituwu.databinding.FragmentFollowBinding
import com.bnawan.gituwu.model.User
import com.bnawan.gituwu.ui.adapter.ListUserAdapter
import com.bnawan.gituwu.ui.adapter.OnUserClickCallback
import com.bnawan.gituwu.ui.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: FollowViewModel
    private val listUsers = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(TAB_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME) ?: ""
        val viewModelFactory = FollowViewModelFactory(username)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FollowViewModel::class.java)

        binding.listFollow.setHasFixedSize(true)

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            onLoading(isLoading)
        }

        viewModel.responseHandler.observe(viewLifecycleOwner) { response ->
            response.getContentIfNotHandled()?.let { responseHandler ->
                when (responseHandler.status) {
                    true -> onSuccess()
                    false -> onFailed(responseHandler.message)
                }
            }
        }

        showRecyclerList()

        viewModel.listUser.observe(viewLifecycleOwner) { users ->
            listUsers.apply {
                clear()
                addAll(users)
            }
            binding.listFollow.adapter?.notifyDataSetChanged()
        }

        when (index) {
            1 -> viewModel.findFollower()
            2 -> viewModel.findFollowing()
            else -> viewModel.findFollower()
        }
    }

    private fun showRecyclerList() {
        if (activity?.applicationContext?.resources?.configuration?.orientation
            == Configuration.ORIENTATION_LANDSCAPE
        ) {
            binding.listFollow.layoutManager = GridLayoutManager(context, 2)
        } else {
            binding.listFollow.layoutManager = LinearLayoutManager(context)
        }
        val listUserAdapter = ListUserAdapter(listUsers)
        binding.listFollow.adapter = listUserAdapter

        listUserAdapter.setOnUserClickCallback(object : OnUserClickCallback {
            override fun onItemClicked(username: String) {
                moveToDetail(username)
            }
        })
    }

    private fun moveToDetail(username: String) {
        val detailActivity = Intent(activity, DetailActivity::class.java)
        detailActivity.putExtra(DetailActivity.DATA_USER, username)
        startActivity(detailActivity)
    }

    private fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                followLoading.visibility = View.VISIBLE
                noFollowIcon.visibility = View.GONE
                noFollowText.visibility = View.GONE
                listFollow.visibility = View.GONE
            }
        } else {
            binding.followLoading.visibility = View.GONE
        }
    }

    private fun onSuccess() {
        binding.apply {
            noFollowIcon.visibility = View.GONE
            noFollowText.visibility = View.GONE
            listFollow.visibility = View.VISIBLE
        }
    }

    private fun onFailed(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        binding.apply {
            noFollowIcon.visibility = View.VISIBLE
            noFollowText.visibility = View.VISIBLE
            listFollow.visibility = View.GONE
        }
    }

    companion object {
        const val TAB_NUMBER = "tab_number"
        const val ARG_USERNAME = "username"
    }
}