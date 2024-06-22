package com.example.annaqah

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annaqah.adapter.AdminCasaAdapter
import com.example.annaqah.adapter.AdminDjellabaAdapter
import com.example.annaqah.adapter.AdminHoceimaAdapter
import com.example.annaqah.adapter.HoceimaAdapter
import com.example.annaqah.data.ItemData
import com.example.annaqah.databinding.ActivityHoceimaBinding
import com.example.annaqah.databinding.FragmentAdminHomeBinding
import com.example.annaqah.databinding.FragmentAdminOrdersBinding
import com.example.annaqah.databinding.FragmentProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AdminHomeFragment : Fragment() {

    private lateinit var binding: FragmentAdminHomeBinding
    private lateinit var database: DatabaseReference
    private lateinit var djellabaAdapter: AdminDjellabaAdapter
    private lateinit var casaAdapter: AdminCasaAdapter
    private lateinit var hoceimaAdapter: AdminHoceimaAdapter

    private val djellabaItems = mutableListOf<ItemData>()
    private val casaItems = mutableListOf<ItemData>()
    private val hoceimaItems = mutableListOf<ItemData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addItem.setOnClickListener {
            val intent = Intent(requireContext(), AdminAddItemActivity::class.java)
            startActivity(intent)
        }

        djellabaAdapter = AdminDjellabaAdapter(requireContext(), djellabaItems)
        binding.adminDjellabaRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.adminDjellabaRV.adapter = djellabaAdapter

        database = FirebaseDatabase.getInstance().getReference("shopItem/Djellaba")
        fetchDjellabaItems()


        casaAdapter = AdminCasaAdapter(requireContext(), casaItems)
        binding.adminCasaRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.adminCasaRV.adapter = casaAdapter

        database = FirebaseDatabase.getInstance().getReference("shopItem/Casa")
        fetchCasaItems()


        hoceimaAdapter = AdminHoceimaAdapter(requireContext(), hoceimaItems)
        binding.adminHoceimaRV.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.adminHoceimaRV.adapter = hoceimaAdapter

        database = FirebaseDatabase.getInstance().getReference("shopItem/Hoceima")
        fetchHoceimaItems()
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
