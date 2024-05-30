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
import com.example.myapplication.databinding.ItemSectionHeaderBinding
import com.example.myapplication.model.Friend

class FriendAdapter(private val friends: List<Friend>, private val onSelectionChanged: (Boolean) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1

        private val HANGUL_INITIALS = listOf(
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
        )
    }

    private val sectionedFriends: Map<Char, List<Friend>> = friends.groupBy { getInitial(it.name.first()) }
    private val sections = sectionedFriends.keys.sorted()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_VIEW_TYPE_HEADER) {
            val binding = ItemSectionHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SectionHeaderViewHolder(binding)
        } else {
            val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FriendViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendViewHolder) {
            val friend = getFriendForPosition(position)
            holder.bind(friend)
        } else if (holder is SectionHeaderViewHolder) {
            val section = getSectionForPosition(position)
            holder.bind(section)
        }
    }

    override fun getItemCount(): Int = sections.sumOf { sectionedFriends[it]?.size ?: 0 + 1 }

    override fun getItemViewType(position: Int): Int {
        return if (isSectionHeaderPosition(position)) {
            ITEM_VIEW_TYPE_HEADER
        } else {
            ITEM_VIEW_TYPE_ITEM
        }
    }

    private fun isSectionHeaderPosition(position: Int): Boolean {
        var pos = position
        sections.forEach {
            if (pos == 0) return true
            pos -= (sectionedFriends[it]?.size ?: 0) + 1
        }
        return false
    }

    private fun getSectionForPosition(position: Int): Char {
        var pos = position
        sections.forEach { key ->
            if (pos == 0) return key
            pos -= (sectionedFriends[key]?.size ?: 0) + 1
        }
        throw IllegalStateException("Invalid position")
    }

    private fun getFriendForPosition(position: Int): Friend {
        var pos = position
        sections.forEach { key ->
            val friendsInSection = sectionedFriends[key] ?: emptyList()
            if (pos <= friendsInSection.size) return friendsInSection[pos - 1]
            pos -= friendsInSection.size + 1
        }
        throw IllegalStateException("Invalid position")
    }

    private fun getInitial(char: Char): Char {
        val unicode = char.code
        if (unicode in 0xAC00..0xD7A3) {
            val initialIndex = (unicode - 0xAC00) / (28 * 21)
            return HANGUL_INITIALS[initialIndex]
        }
        return char
    }

    inner class FriendViewHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val friend = getFriendForPosition(adapterPosition)
                friend.isSelected = !friend.isSelected
                notifyItemChanged(adapterPosition)
                onSelectionChanged(isAnyFriendSelected())
            }

            binding.favoriteIcon.setOnClickListener {
                val friend = getFriendForPosition(adapterPosition)
                friend.isFavorite = !friend.isFavorite
                notifyItemChanged(adapterPosition)
            }
        }

        fun bind(friend: Friend) {
            binding.friendName.text = friend.name
            val drawable = binding.friendImage.drawable as VectorDrawable

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

    inner class SectionHeaderViewHolder(private val binding: ItemSectionHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(section: Char) {
            binding.sectionHeader.text = section.toString()
        }
    }

    private fun isAnyFriendSelected(): Boolean {
        return friends.any { it.isSelected }
    }
}
