package com.aryanto.githubuser.ui.fragment.followers

import android.os.Bundle
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment : Fragment() {
    private lateinit var adapter: FollowersAdapter
    private lateinit var recyclerView: RecyclerView
    private val apiService: ApiService by lazy {
        RetrofitInstance.serviceApi
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_followers, container, false)
        recyclerView = view.findViewById(R.id.rv_followers)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FollowersAdapter(emptyList())
        recyclerView.adapter = adapter

        val username = arguments?.getString(ARG_USERNAME)
        username?.let {
            getFollowers(it)
        }

        return view
    }

    private fun getFollowers(username: String) {
        apiService.getFollowers(username).enqueue(object : Callback<List<Item>>{
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful){
                    val followersList = response.body()
                    followersList?.let {
                        adapter.setData(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    companion object {
        private const val ARG_USERNAME = "username"
        @JvmStatic
        fun newInstance(username: String): FollowersFragment{
            val fragment = FollowersFragment()
            val args = Bundle()
            args.putString(ARG_USERNAME, username)
            fragment.arguments = args
            return fragment
        }
    }
}