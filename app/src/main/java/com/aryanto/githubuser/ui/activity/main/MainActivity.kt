package com.aryanto.githubuser.ui.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubuser.Item
import com.aryanto.githubuser.R
import com.aryanto.githubuser.ResponseGithub
import com.aryanto.githubuser.data.remote.network.RetrofitInstance
import com.aryanto.githubuser.ui.activity.detail.DetailActivity
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(emptyList()){item ->
            showDetails(item)
        }

        recyclerView = findViewById(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchBar = findViewById(R.id.search_bar_material)
        searchView = findViewById(R.id.search_view_material)

        getUsers()
        setSearch()

    }
    private fun getUsers() {
        val call: Call<List<Item>> = RetrofitInstance.serviceApi.getUser()

        call.enqueue(object : Callback<List<Item>>{
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful){
                    val users: List<Item>? = response.body()
                    users?.let {
                        adapter = MainAdapter(it){item ->
                            showDetails(item)
                        }
                        recyclerView.adapter= adapter
                    }
                }
            }
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.e("LOG", "Error: ${t.message}")
            }
        })
    }
    private fun showDetails(item: Item){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("username", item.login)
        startActivity(intent)
    }
    private fun setSearch(){
        searchView.setupWithSearchBar(searchBar)
        searchView.editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                val searchText = v.text.toString()
                searchView.hide()
                performSearch(searchText)
                true
            } else {
                false
            }
        }
    }
    private fun performSearch(query: String) {
        val call: Call<ResponseGithub> = RetrofitInstance.serviceApi.searchUser(query)

        call.enqueue(object : Callback<ResponseGithub>{
            override fun onResponse(
                call: Call<ResponseGithub>,
                response: Response<ResponseGithub>
            ) {
                if (response.isSuccessful){
                    val searchResults: ResponseGithub? = response.body()

                    searchResults?.let {
                        adapter.setData(it.items)
                        recyclerView.scrollToPosition(0)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseGithub>, t: Throwable) {
                Log.e("LOG", "Search Error: ${t.message}")
            }

        })
    }


}