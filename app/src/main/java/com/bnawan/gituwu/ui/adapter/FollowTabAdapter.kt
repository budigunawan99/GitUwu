package com.bnawan.gituwu.ui.adapter

import android.os.Bundle
import android.provider.Settings.Global.putInt
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bnawan.gituwu.ui.follow.FollowFragment

class FollowTabAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.TAB_NUMBER, position + 1)
            putString(FollowFragment.ARG_USERNAME, username)
        }
        return fragment
    }

}