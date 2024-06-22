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

class CasaAdapter(private val context: Context, private val casaItems: List<ItemData>) : RecyclerView.Adapter<CasaAdapter.CasaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CasaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CasaViewHolder, position: Int) {
        val casaItem = casaItems[position]
        Glide.with(context).load(casaItem.imageUrl).into(holder.itemImage)
        holder.textViewItem.text = casaItem.name
        holder.textViewPrice.text = casaItem.price

        holder.chooseButton.setOnClickListener {
            val intent = Intent(context, ItemActivity::class.java).apply {
                putExtra("image", casaItem.imageUrl)
                putExtra("name", casaItem.name)
                putExtra("price", casaItem.price)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return casaItems.size
    }

    class CasaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val textViewItem: TextView = view.findViewById(R.id.itemName)
        val textViewPrice: TextView = view.findViewById(R.id.itemPrice)
        val chooseButton: Button = view.findViewById(R.id.chooseButton)
    }
}
