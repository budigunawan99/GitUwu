package com.bnawan.gituwu.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bnawan.gituwu.databinding.ItemUserBinding
import com.bnawan.gituwu.model.User
import com.bnawan.gituwu.util.UserDiffCallback
import com.bumptech.glide.Glide

class ListUserAdapter() :
    RecyclerView.Adapter<ListUserViewHolder>() {

    private lateinit var onUserClickCallback: OnUserClickCallback
    private val listUser = ArrayList<User>()

    fun setListUser(listUser: List<User>) {
        val diffCallback = UserDiffCallback(this.listUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnUserClickCallback(onUserClickCallback: OnUserClickCallback) {
        this.onUserClickCallback = onUserClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {

        holder.binding.apply {
            userUsername.text = listUser[position].username
            userUrl.text = listUser[position].url

            Glide.with(holder.itemView.context)
                .load(listUser[position].avatarUrl)
                .circleCrop()
                .into(userImg)

            btnDetail.setOnClickListener {
                onUserClickCallback.onItemClicked(listUser[position].username)
            }
        }
    }

    override fun getItemCount(): Int = listUser.size
}