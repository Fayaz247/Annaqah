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

class DjellabaAdapter(private val context: Context, private val djellabaItems: List<ItemData>) : RecyclerView.Adapter<DjellabaAdapter.DjellabaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DjellabaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return DjellabaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DjellabaViewHolder, position: Int) {
        val djellabaItem = djellabaItems[position]
        Glide.with(context).load(djellabaItem.imageUrl).into(holder.itemImage)
        holder.textViewItem.text = djellabaItem.name
        holder.textViewPrice.text = djellabaItem.price

        holder.chooseButton.setOnClickListener {
            val intent = Intent(context, ItemActivity::class.java).apply {
                putExtra("image", djellabaItem.imageUrl)
                putExtra("name", djellabaItem.name)
                putExtra("price", djellabaItem.price)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return djellabaItems.size
    }

    class DjellabaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val textViewItem: TextView = view.findViewById(R.id.itemName)
        val textViewPrice: TextView = view.findViewById(R.id.itemPrice)
        val chooseButton: Button = view.findViewById(R.id.chooseButton)
    }
}
