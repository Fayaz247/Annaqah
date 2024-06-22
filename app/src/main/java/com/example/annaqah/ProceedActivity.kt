package com.example.annaqah

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.annaqah.data.ConfirmedData
import com.example.annaqah.data.PaymentData
import com.example.annaqah.databinding.ActivityProceedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.annaqah.data.OrdersData
import java.util.Date
import java.util.Locale


class ProceedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProceedBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProceedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference

        val confirmedData = intent.getParcelableExtra<ConfirmedData>("CONFIRMED_DATA")
        confirmedData?.let {
            Glide.with(this).load(it.confirmedImage).into(binding.confirmedImage)
            binding.confirmedItemName.text = it.confirmedItemName
            binding.confirmedPrice.text = it.confirmedPrice
            binding.confirmedSize.text = it.confirmedSize
        }

        binding.confirmButton.setOnClickListener {
            val paymentData = collectPaymentData()
            savePaymentDataToFirebase(paymentData)
        }
    }

    private fun collectPaymentData(): PaymentData {
        val email = binding.paymentEmail.text.toString()
        val address = binding.paymentAddress.text.toString()
        val nameOnCard = binding.paymentName.text.toString()
        val cardNumber = binding.paymentCardNumber.text.toString()
        val expiry = binding.paymentExpiry.text.toString()
        val cvv = binding.paymentCVV.text.toString()

        return PaymentData(
            email = email,
            address = address,
            nameOnCard = nameOnCard,
            cardNumber = cardNumber,
            expiry = expiry,
            cvv = cvv
        )
    }

    private fun savePaymentDataToFirebase(paymentData: PaymentData) {
        val itemName = binding.confirmedItemName.text.toString()
        val itemPrice = binding.confirmedPrice.text.toString()
        val itemSize = binding.confirmedSize.text.toString()

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        if (paymentData.email.isEmpty() ||
            paymentData.address.isEmpty() ||
            paymentData.nameOnCard.isEmpty() ||
            paymentData.cardNumber.isEmpty() ||
            paymentData.expiry.isEmpty() ||
            paymentData.cvv.isEmpty()
        ) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Get the number of orders to set the orderID
        database.child("user").child(userId).child("orderData").get().addOnSuccessListener { snapshot ->
            val orderCount = snapshot.childrenCount.toInt() + 1 // Increment by 1 for the new order
            val emailPrefix = FirebaseAuth.getInstance().currentUser?.email?.take(3)?.uppercase(Locale.getDefault())
            val orderID = ("$emailPrefix" + "$orderCount")

            val orderData = OrdersData(
                orderID = orderID,
                itemImage = R.drawable.annaqah_logo,
                itemName = itemName,
                itemPrice = itemPrice,
                itemSize = itemSize,
                date = currentDate
            )

            // Save paymentData and orderData to Firebase
            val userRef = database.child("user").child(userId)
            userRef.child("paymentData").setValue(paymentData)
                .addOnSuccessListener {
                    // Payment data saved successfully, now save orderData
                    userRef.child("orderData").child(orderID).setValue(orderData)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Payment and Order Data Saved Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, ConfirmedActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to Save Order Data", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to Save Payment Data", Toast.LENGTH_SHORT).show()
                }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to Get Order Count", Toast.LENGTH_SHORT).show()
        }
    }
}
