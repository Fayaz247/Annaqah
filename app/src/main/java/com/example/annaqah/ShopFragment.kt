package com.example.annaqah

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.annaqah.adapter.ShopAdapter
import com.example.annaqah.data.ShopItem

class ShopFragment : Fragment(), ShopAdapter.OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        val shopItems = listOf(
            ShopItem(R.drawable.sapphire_djellaba, "Djellaba Collection"),
            ShopItem(R.drawable.arctic_gandoura, "Hoceima Collection"),
            ShopItem(R.drawable.emerald_gandoura, "Casa Collection")
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.shopRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ShopAdapter(shopItems, this)

        return view
    }

    override fun onItemClick(shopItem: ShopItem) {
        val intent = when (shopItem.tag) {
            "Djellaba Collection" -> Intent(activity, DjellabaActivity::class.java)
            "Hoceima Collection" -> Intent(activity, HoceimaActivity::class.java)
            "Casa Collection" -> Intent(activity, CasaActivity::class.java)
            else -> return
        }
        startActivity(intent)
    }
}
