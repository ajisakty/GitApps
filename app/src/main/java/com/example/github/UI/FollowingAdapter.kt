package com.example.github.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.data.response.UsersFollowingResponseItem
import com.example.github.data.response.UsersResponseItem
import com.example.github.databinding.FollowersItemBinding
import com.example.github.databinding.UserItemBinding

class FollowingAdapter: ListAdapter<UsersFollowingResponseItem, FollowingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersFollowingResponseItem>(){
            override fun areItemsTheSame(
                oldItem: UsersFollowingResponseItem,
                newItem: UsersFollowingResponseItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: UsersFollowingResponseItem,
                newItem: UsersFollowingResponseItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class MyViewHolder(private val binding: FollowersItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersFollowingResponseItem){
            binding.tvFollowingUserName.text = user.login
            Glide.with(binding.ivUserFollowing)
                .load(user.avatarUrl)
                .into(binding.ivUserFollowing)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = FollowersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}