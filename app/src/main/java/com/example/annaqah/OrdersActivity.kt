package com.example.annaqah

import OrdersAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annaqah.data.OrderData
import com.example.annaqah.databinding.ActivityOrdersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding
    private lateinit var database: DatabaseReference
    private lateinit var ordersAdapter: OrdersAdapter
    private val ordersList = mutableListOf<OrderData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backButton.setOnClickListener {
            finish()
        }
        binding.orderRecyclerView.layoutManager = LinearLayoutManager(this)
        ordersAdapter = OrdersAdapter(ordersList)
        binding.orderRecyclerView.adapter = ordersAdapter

        database = FirebaseDatabase.getInstance().reference

        fetchOrders()
    }

    private fun fetchOrders() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            showToast("User is not logged in.")
            return
        }

        val ordersRef = database.child("user").child(userId).child("orderData")
        ordersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ordersList.clear()
                for (orderSnapshot in snapshot.children) {
                    val order = orderSnapshot.getValue(OrderData::class.java)
                    if (order != null) {
                        ordersList.add(order)
                    }
                }
                ordersAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Failed to load orders.")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
