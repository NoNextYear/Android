package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemFriendBinding
import com.example.myapplication.model.Friend

class FriendAdapter(private val friends: List<Friend>) :
    RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(friends[position])
    }

    override fun getItemCount(): Int = friends.size

    inner class FriendViewHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val friend = friends[adapterPosition]
                friend.isSelected = !friend.isSelected
                notifyItemChanged(adapterPosition)
            }
        }

        fun bind(friend: Friend) {
            binding.friendName.text = friend.name
            binding.friendImage.setImageResource(friend.imageResId)
            if (friend.isSelected) {
                binding.friendImage.setColorFilter(binding.root.context.getColor(R.color.selected_item_color))
                binding.checkImage.visibility = View.VISIBLE
            } else {
                binding.friendImage.clearColorFilter()
                binding.checkImage.visibility = View.GONE
            }
        }
    }
}
