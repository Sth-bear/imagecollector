package com.example.imagecollector.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.imagecollector.data.ImageItem
import com.example.imagecollector.databinding.ItemSearchBinding

class SearchFragAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itemList = listOf<ImageItem>()

    interface ItemClick {
        fun onClick(view:View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = itemList[position]
        (holder as Holder).itemView.setOnClickListener {
            itemClick?.onClick(it,position)
        }
        holder.bind(currentItem)
    }

    class Holder(val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(imageItem: ImageItem) {
            binding.apply {
                tvItemDate.text = imageItem.datetime
                tvItemFrom.text = imageItem.display_sitename
                ivItem.setImageURI(imageItem.image_url.toUri())
            }
        }
    }

    fun listUpdate(items: List<ImageItem>) {
        this.itemList = items
        notifyDataSetChanged()
    }

}