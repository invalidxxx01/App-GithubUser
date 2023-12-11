package com.aryanto.githubuser.ui.fragment.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubuser.Item
import com.aryanto.githubuser.R
import com.bumptech.glide.Glide

class FollowingAdapter(
    private var itemList: List<Item>
): RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    class FollowingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivItemFollowing: ImageView = view.findViewById(R.id.iv_image_item_main)
        val tvItemFollowing: TextView = view.findViewById(R.id.tv_name_item_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return FollowingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val item = itemList[position]

        holder.tvItemFollowing.text = item.login

        Glide.with(holder.itemView.context)
            .load(item.avatar_url)
            .placeholder(R.drawable.ic_person)
            .into(holder.ivItemFollowing)
    }

    fun setData(newFollowing: List<Item>) {
        itemList = newFollowing
        notifyDataSetChanged()
    }
}