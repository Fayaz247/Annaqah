package com.example.annaqah.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.annaqah.R
import com.example.annaqah.data.OrderData
import com.example.annaqah.databinding.AdminOrderItemBinding
import com.example.annaqah.databinding.OrderItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdminOrderAdapter(private val ordersList: List<OrderData>) : RecyclerView.Adapter<AdminOrderAdapter.AdminOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminOrderViewHolder {
        val binding = AdminOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminOrderViewHolder, position: Int) {
        val order = ordersList[position]
        holder.bind(order)
    }

    override fun getItemCount() = ordersList.size

    inner class AdminOrderViewHolder(private val binding: AdminOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderData) {
            binding.txtOrder.text = order.orderID
            binding.txtOrderName.text = order.itemName
            binding.txtOrderPrice.text = order.itemPrice
            binding.txtOrderStatus.text = order.status

            binding.btnComplete.setOnClickListener {
                updateOrderStatus(order, "Complete")
            }

            binding.btnPending.setOnClickListener {
                updateOrderStatus(order, "Pending")
            }
        }

        private fun updateOrderStatus(order: OrderData, newStatus: String) {
            val database = FirebaseDatabase.getInstance().reference
            val orderRef = database.child("user").child(order.userID).child("orderData").child(order.orderID)

            orderRef.child("status").setValue(newStatus).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.txtOrderStatus.text = newStatus
                    order.status = newStatus // Update the local data
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(binding.root.context, "Failed to update status", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

