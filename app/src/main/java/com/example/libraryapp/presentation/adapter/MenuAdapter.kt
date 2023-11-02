package com.example.libraryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.libraryapp.databinding.MenuItemBinding
import com.example.libraryapp.presentation.adapter.viewholder.MenuItemViewHolder
import com.example.libraryapp.ui.model.MenuItem


class MenuAdapter(
    private val onItemClick: () -> Unit
) : ListAdapter<MenuItem, MenuItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder =
        MenuItemViewHolder(
            MenuItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            current.onClick()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem.title == newItem.title
        }

    }

}