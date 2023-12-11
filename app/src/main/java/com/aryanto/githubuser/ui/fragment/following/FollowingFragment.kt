package com.aryanto.githubuser.ui.fragment.following

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubuser.DetailUser
import com.aryanto.githubuser.Item
import com.aryanto.githubuser.R
import com.aryanto.githubuser.data.remote.network.ApiService
import com.aryanto.githubuser.data.remote.network.RetrofitInstance
import com.aryanto.githubuser.ui.fragment.followers.FollowersAdapter
import com.aryanto.githubuser.ui.fragment.followers.FollowersFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment : Fragment() {
    private lateinit var adapter: FollowingAdapter
    private lateinit var recyclerView: RecyclerView
    private val apiService: ApiService by lazy {
        RetrofitInstance.serviceApi
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_following, container, false)
        recyclerView = view.findViewById(R.id.rv_following)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FollowingAdapter(emptyList())
        recyclerView.adapter = adapter

        val username = arguments?.getString(ARG_USERNAME)
        username?.let {
            getFollowing(it)
        }

        return view
    }

    private fun getFollowing(username: String) {
        apiService.getFollowing(username).enqueue(object : Callback<List<Item>>{
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful){
                    val followingList = response.body()
                    followingList?.let {
                        adapter.setData(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.e("LOG", "FollowingFragment Error getting following: ${t.message}")
            }

        })
    }

    companion object {
        private const val ARG_USERNAME = "username"
        @JvmStatic
        fun newInstance(username: String):FollowingFragment{
            val fragment = FollowingFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME, username)
            fragment.arguments = args
            return fragment
        }
    }
}