package com.aryanto.githubuser.ui.fragment.followers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubuser.Item
import com.aryanto.githubuser.R
import com.bumptech.glide.Glide

class FollowersAdapter(
    private var itemList: List<Item>
): RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    class FollowersViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val ivItemFollowers: ImageView = view.findViewById(R.id.iv_image_item_main)
        val tvItemFollowers: TextView = view.findViewById(R.id.tv_name_item_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return FollowersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val item = itemList[position]

        holder.tvItemFollowers.text = item.login

        Glide.with(holder.itemView.context)
            .load(item.avatar_url)
            .placeholder(R.drawable.ic_person)
            .into(holder.ivItemFollowers)
    }

    fun setData(newFollowers: List<Item>){
        itemList = newFollowers
        notifyDataSetChanged()
    }
}