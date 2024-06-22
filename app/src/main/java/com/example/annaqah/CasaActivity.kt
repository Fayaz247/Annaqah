package com.example.annaqah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.annaqah.adapter.CasaAdapter
import com.example.annaqah.data.ItemData
import com.example.annaqah.databinding.ActivityCasaBinding
import com.google.firebase.database.*

class CasaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCasaBinding
    private lateinit var database: DatabaseReference
    private lateinit var casaAdapter: CasaAdapter
    private val casaItems = mutableListOf<ItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        casaAdapter = CasaAdapter(this, casaItems)
        binding.casaRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.casaRecyclerView.adapter = casaAdapter

        database = FirebaseDatabase.getInstance().getReference("shopItem/Casa")
        fetchCasaItems()
    }

    private fun fetchCasaItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                casaItems.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(ItemData::class.java)
                    if (item != null) {
                        casaItems.add(item)
                    }
                }
                casaAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors.
            }
        })
    }
}
