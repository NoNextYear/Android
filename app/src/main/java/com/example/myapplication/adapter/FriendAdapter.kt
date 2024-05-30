package com.example.myapplication.adapter

import android.graphics.PorterDuff
import android.graphics.drawable.VectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

            binding.favoriteIcon.setOnClickListener {
                val friend = friends[adapterPosition]
                friend.isFavorite = !friend.isFavorite
                notifyItemChanged(adapterPosition)
            }
        }

        fun bind(friend: Friend) {
            binding.friendName.text = friend.name
            val drawable = binding.friendImage.drawable as VectorDrawable

            // strokeWidth를 설정하는 대신 ColorFilter를 사용하여 테두리 색상 변경
            if (friend.isSelected) {
                drawable.setColorFilter(ContextCompat.getColor(binding.root.context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
                binding.checkImage.visibility = View.VISIBLE
            } else {
                drawable.setColorFilter(ContextCompat.getColor(binding.root.context, android.R.color.black), PorterDuff.Mode.SRC_IN)
                binding.checkImage.visibility = View.GONE
            }
            binding.friendImage.setImageDrawable(drawable)

            if (friend.isFavorite) {
                binding.favoriteIcon.setColorFilter(ContextCompat.getColor(binding.root.context, android.R.color.holo_orange_light), PorterDuff.Mode.SRC_IN)
            } else {
                binding.favoriteIcon.clearColorFilter()
                binding.favoriteIcon.setImageResource(R.drawable.ic_star)
            }
        }
    }
}
