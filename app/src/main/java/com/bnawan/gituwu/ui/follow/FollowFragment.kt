package com.bnawan.gituwu.ui.follow

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
import com.bnawan.gituwu.data.Result
import com.bnawan.gituwu.databinding.FragmentFollowBinding
import com.bnawan.gituwu.ui.adapter.ListUserAdapter
import com.bnawan.gituwu.ui.adapter.OnUserClickCallback
import com.bnawan.gituwu.ui.detail.DetailActivity

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowViewModel
    private lateinit var adapter: ListUserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(TAB_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME) ?: ""
        val viewModelFactory = FollowViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, viewModelFactory)[FollowViewModel::class.java]

        binding.listFollow.setHasFixedSize(true)

        showRecyclerList()

        when (index) {
            1 -> {
                viewModel.findFollower(username).observe(viewLifecycleOwner) { result ->
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
            2 -> {
                viewModel.findFollowing(username).observe(viewLifecycleOwner) { result ->
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
    }

    private fun showRecyclerList() {
        if (activity?.applicationContext?.resources?.configuration?.orientation
            == Configuration.ORIENTATION_LANDSCAPE
        ) {
            binding.listFollow.layoutManager = GridLayoutManager(context, 2)
        } else {
            binding.listFollow.layoutManager = LinearLayoutManager(context)
        }
        adapter = ListUserAdapter()
        binding.listFollow.adapter = adapter

        adapter.setOnUserClickCallback(object : OnUserClickCallback {
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

    private fun onLoading() {
        binding.apply {
            followLoading.visibility = View.VISIBLE
            noFollowIcon.visibility = View.GONE
            noFollowText.visibility = View.GONE
            listFollow.visibility = View.GONE
        }
    }

    private fun onSuccess() {
        binding.apply {
            followLoading.visibility = View.GONE
            noFollowIcon.visibility = View.GONE
            noFollowText.visibility = View.GONE
            listFollow.visibility = View.VISIBLE
        }
    }

    private fun onFailed(message: String) {
        binding.apply {
            followLoading.visibility = View.GONE
            noFollowIcon.visibility = View.VISIBLE
            noFollowText.visibility = View.VISIBLE
            noFollowText.text = message
            listFollow.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAB_NUMBER = "tab_number"
        const val ARG_USERNAME = "username"
    }
}