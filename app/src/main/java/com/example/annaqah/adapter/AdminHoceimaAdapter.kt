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

class AdminHoceimaAdapter(private val context: Context, private val hoceimaItems: List<ItemData>) : RecyclerView.Adapter<AdminHoceimaAdapter.AdminHoceimaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminHoceimaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_card, parent, false)
        return AdminHoceimaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminHoceimaViewHolder, position: Int) {
        val hoceimaItem = hoceimaItems[position]
        Glide.with(context).load(hoceimaItem.imageUrl).into(holder.itemImage)
        holder.textViewItem.text = hoceimaItem.name
        holder.textViewPrice.text = hoceimaItem.price

    }

    override fun getItemCount(): Int {
        return hoceimaItems.size
    }

    class AdminHoceimaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val textViewItem: TextView = view.findViewById(R.id.itemName)
        val textViewPrice: TextView = view.findViewById(R.id.itemPrice)
    }
}
