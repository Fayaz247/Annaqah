package com.example.annaqah.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.annaqah.R
import com.example.annaqah.data.ShopItem

class ShopAdapter(private val shopItems: List<ShopItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(shopItem: ShopItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shop_card, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shopItem = shopItems[position]
        holder.imageView.setImageResource(shopItem.image)
        holder.textView.text = shopItem.tag
        holder.itemView.setOnClickListener {
            listener.onItemClick(shopItem)
        }
    }

    override fun getItemCount(): Int {
        return shopItems.size
    }

    class ShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.shopImage)
        val textView: TextView = view.findViewById(R.id.txtShop)
    }
}
