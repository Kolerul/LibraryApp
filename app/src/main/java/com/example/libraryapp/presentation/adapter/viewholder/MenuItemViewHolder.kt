package com.example.libraryapp.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.databinding.MenuItemBinding
import com.example.libraryapp.ui.model.MenuItem

class MenuItemViewHolder(
    private val binding: MenuItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MenuItem) {
        binding.apply {
            icon.setImageResource(item.iconId)
            title.text = item.title

        }
    }
}