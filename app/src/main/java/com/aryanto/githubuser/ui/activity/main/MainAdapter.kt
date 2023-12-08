package com.aryanto.githubuser.ui.activity.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubuser.Item
import com.aryanto.githubuser.R
import com.bumptech.glide.Glide

class MainAdapter(
    private val itemList : List<Item>
):RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    class MainViewHolder(view :View): RecyclerView.ViewHolder(view) {
        val ivItemMain: ImageView = view.findViewById(R.id.iv_image_item_main)
        val tvItemMain: TextView = view.findViewById(R.id.tv_name_item_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = itemList[position]

        holder.tvItemMain.text = item.login
        Glide.with(holder.itemView.context)
            .load(item.avatar_url)
            .into(holder.ivItemMain)
    }
}