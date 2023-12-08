package com.aryanto.githubuser.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubuser.Item
import com.aryanto.githubuser.R
import com.aryanto.githubuser.data.remote.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(emptyList())

        recyclerView = findViewById(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getUsers()

    }

    private fun getUsers() {
        val call: Call<List<Item>> = RetrofitInstance.serviceApi.getUser()

        call.enqueue(object : Callback<List<Item>>{
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful){
                    val users: List<Item>? = response.body()
                    users?.let {
                        adapter = MainAdapter(it)
                        recyclerView.adapter= adapter
                    }
                }
            }
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.e("LOG", "Error: ${t.message}")
            }
        })
    }
}