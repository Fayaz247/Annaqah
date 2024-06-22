package com.example.annaqah.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.annaqah.R
import com.example.annaqah.data.ShopItem

class HomeAdapter(private val homeItems: List<ShopItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(homeItem: ShopItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.featured_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val homeItem = homeItems[position]
        holder.imageView.setImageResource(homeItem.image)
        holder.textView.text = homeItem.tag
        holder.itemView.setOnClickListener {
            listener.onItemClick(homeItem)
        }
    }

    override fun getItemCount(): Int {
        return homeItems.size
    }

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.homeImage)
        val textView: TextView = view.findViewById(R.id.txtHome)
    }
}
