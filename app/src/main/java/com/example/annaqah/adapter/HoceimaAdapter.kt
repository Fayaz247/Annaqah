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

class HoceimaAdapter(private val context: Context, private val hoceimaItems: List<ItemData>) : RecyclerView.Adapter<HoceimaAdapter.HoceimaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoceimaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return HoceimaViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoceimaViewHolder, position: Int) {
        val hoceimaItem = hoceimaItems[position]
        Glide.with(context).load(hoceimaItem.imageUrl).into(holder.itemImage)
        holder.textViewItem.text = hoceimaItem.name
        holder.textViewPrice.text = hoceimaItem.price

        holder.chooseButton.setOnClickListener {
            val intent = Intent(context, ItemActivity::class.java).apply {
                putExtra("image", hoceimaItem.imageUrl)
                putExtra("name", hoceimaItem.name)
                putExtra("price", hoceimaItem.price)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return hoceimaItems.size
    }

    class HoceimaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val textViewItem: TextView = view.findViewById(R.id.itemName)
        val textViewPrice: TextView = view.findViewById(R.id.itemPrice)
        val chooseButton: Button = view.findViewById(R.id.chooseButton)
    }
}
