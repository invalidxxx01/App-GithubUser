package com.aryanto.githubuser.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.aryanto.githubuser.DetailUser
import com.aryanto.githubuser.R
import com.aryanto.githubuser.data.remote.network.RetrofitInstance
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val username = intent.getStringExtra("username")

        if (username != null){
            getUserDetails(username)
        }
    }

    private fun getUserDetails(username: String) {
        val call: Call<DetailUser> = RetrofitInstance.serviceApi.getDetailUser(username)

        call.enqueue(object  : Callback<DetailUser>{

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Log.e("LOG", "DA-Error: ${t.message}")
            }

            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                if (response.isSuccessful){
                    val userDetail: DetailUser? = response.body()
                    userDetail?.let {
                        setUserDetails(it)
                        setViewPager(it)

                    }
                }
            }

        })

    }

    private fun setUserDetails(detailUser: DetailUser) {
        val tvName = findViewById<TextView>(R.id.tv_detail_name)
        val tvUsername = findViewById<TextView>(R.id.tv_detail_username)
        val tvFollowers = findViewById<TextView>(R.id.tv_detail_followers)
        val tvFollowing = findViewById<TextView>(R.id.tv_detail_following)
        val ivUserImage = findViewById<ImageView>(R.id.iv_image_detail)

        tvName.text = detailUser.name
        tvUsername.text = detailUser.login
        tvFollowers.text = detailUser.followers.toString()
        tvFollowing.text = detailUser.following.toString()

        Glide.with(this)
            .load(detailUser.avatar_url)
            .into(ivUserImage)

    }
    private fun setViewPager(userDetails: DetailUser) {
        val viewPager: ViewPager2 = findViewById(R.id.viewpager_detail)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout_detail)
        val adapter = DetailAdapter(this, userDetails)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position->
            tab.text = when (position){
                0 -> "Followers"
                1 -> "Following"
                else -> ""
            }

            tab.contentDescription = when (position) {
                0 -> "Followers Tab"
                1 -> "Following Tab"
                else -> ""
            }

        }.attach()
    }
}