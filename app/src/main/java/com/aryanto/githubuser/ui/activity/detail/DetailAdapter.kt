package com.aryanto.githubuser.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aryanto.githubuser.DetailUser
import com.aryanto.githubuser.Item
import com.aryanto.githubuser.ui.fragment.followers.FollowersFragment
import com.aryanto.githubuser.ui.fragment.following.FollowingFragment

class DetailAdapter(
    activity: AppCompatActivity,
    var userDetail: DetailUser
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FollowersFragment.newInstance(userDetail.login)
            1 -> FollowingFragment.newInstance(userDetail.login)
            else -> throw IllegalAccessException("Invalid position")
        }
    }

}