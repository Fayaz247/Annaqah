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

class AdminCasaAdapter(private val context: Context, private val casaItems: List<ItemData>) : RecyclerView.Adapter<AdminCasaAdapter.AdminCasaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminCasaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_card, parent, false)
        return AdminCasaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminCasaViewHolder, position: Int) {
        val casaItem = casaItems[position]
        Glide.with(context).load(casaItem.imageUrl).into(holder.itemImage)
        holder.textViewItem.text = casaItem.name
        holder.textViewPrice.text = casaItem.price

    }

    override fun getItemCount(): Int {
        return casaItems.size
    }

    class AdminCasaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val textViewItem: TextView = view.findViewById(R.id.itemName)
        val textViewPrice: TextView = view.findViewById(R.id.itemPrice)
    }
}
