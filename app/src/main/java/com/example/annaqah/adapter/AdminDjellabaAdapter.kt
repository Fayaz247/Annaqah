package com.example.annaqah.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.annaqah.ItemActivity
import com.example.annaqah.R
import com.example.annaqah.data.ItemData

class AdminDjellabaAdapter(private val context: Context, private val djellabaItems: List<ItemData>) : RecyclerView.Adapter<AdminDjellabaAdapter.AdminDjellabaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDjellabaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_card, parent, false)
        return AdminDjellabaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminDjellabaViewHolder, position: Int) {
        val djellabaItem = djellabaItems[position]
        Glide.with(context).load(djellabaItem.imageUrl).into(holder.itemImage)
        holder.textViewItem.text = djellabaItem.name
        holder.textViewPrice.text = djellabaItem.price

    }

    override fun getItemCount(): Int {
        return djellabaItems.size
    }

    class AdminDjellabaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val textViewItem: TextView = view.findViewById(R.id.itemName)
        val textViewPrice: TextView = view.findViewById(R.id.itemPrice)
    }
}
