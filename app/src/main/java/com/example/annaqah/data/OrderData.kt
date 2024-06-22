package com.example.annaqah.data

data class OrderData(
    val orderID: String = "",
    val itemName: String = "",
    val itemPrice: String = "",
    var status: String = "Pending",
    val userID: String = ""
)

