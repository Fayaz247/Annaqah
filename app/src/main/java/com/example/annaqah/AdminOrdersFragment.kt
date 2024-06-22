package com.example.annaqah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.annaqah.adapter.AdminOrderAdapter
import com.example.annaqah.data.OrderData
import com.example.annaqah.databinding.FragmentAdminOrdersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminOrdersFragment : Fragment() {

    private lateinit var binding: FragmentAdminOrdersBinding

    private lateinit var database: DatabaseReference
    private lateinit var ordersAdapter: AdminOrderAdapter
    private val ordersList = mutableListOf<OrderData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.adminOrderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        ordersAdapter = AdminOrderAdapter(ordersList)
        binding.adminOrderRecyclerView.adapter = ordersAdapter

        database = FirebaseDatabase.getInstance().reference

        fetchOrders()
    }

    private fun fetchOrders() {
        val ordersRef = database.child("user")
        ordersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ordersList.clear()
                for (userSnapshot in snapshot.children) {
                    val userId = userSnapshot.key ?: continue
                    val orderDataSnapshot = userSnapshot.child("orderData")
                    for (orderSnapshot in orderDataSnapshot.children) {
                        val order = orderSnapshot.getValue(OrderData::class.java)
                        if (order != null) {
                            ordersList.add(order.copy(userID = userId))
                        }
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
