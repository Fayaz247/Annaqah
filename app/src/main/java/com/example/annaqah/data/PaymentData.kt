package com.example.annaqah.data

data class PaymentData(
    val email: String = "",
    val address: String = "",
    val nameOnCard: String = "",
    val cardNumber: String = "",
    val expiry: String = "",
    val cvv: String = ""
)
