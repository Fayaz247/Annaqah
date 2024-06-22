package com.example.annaqah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.annaqah.adapter.DjellabaAdapter
import com.example.annaqah.data.ItemData
import com.example.annaqah.databinding.ActivityDjellabaBinding
import com.google.firebase.database.*

class DjellabaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDjellabaBinding
    private lateinit var database: DatabaseReference
    private lateinit var djellabaAdapter: DjellabaAdapter
    private val djellabaItems = mutableListOf<ItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDjellabaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        djellabaAdapter = DjellabaAdapter(this, djellabaItems)
        binding.djellabaRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.djellabaRecyclerView.adapter = djellabaAdapter

        database = FirebaseDatabase.getInstance().getReference("shopItem/Djellaba")
        fetchDjellabaItems()
    }

    private fun fetchDjellabaItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                djellabaItems.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(ItemData::class.java)
                    if (item != null) {
                        djellabaItems.add(item)
                    }
                }
                djellabaAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors.
            }
        })
    }
}
