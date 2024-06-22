package com.example.annaqah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.annaqah.adapter.HoceimaAdapter
import com.example.annaqah.data.ItemData
import com.example.annaqah.databinding.ActivityHoceimaBinding
import com.google.firebase.database.*

class HoceimaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHoceimaBinding
    private lateinit var database: DatabaseReference
    private lateinit var hoceimaAdapter: HoceimaAdapter
    private val hoceimaItems = mutableListOf<ItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHoceimaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        hoceimaAdapter = HoceimaAdapter(this, hoceimaItems)
        binding.hoceimaRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.hoceimaRecyclerView.adapter = hoceimaAdapter

        database = FirebaseDatabase.getInstance().getReference("shopItem/Hoceima")
        fetchHoceimaItems()
    }

    private fun fetchHoceimaItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hoceimaItems.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(ItemData::class.java)
                    if (item != null) {
                        hoceimaItems.add(item)
                    }
                }
                hoceimaAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors.
            }
        })
    }
}
