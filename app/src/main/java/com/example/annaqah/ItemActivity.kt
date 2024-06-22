package com.example.annaqah

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.annaqah.data.ConfirmedData
import com.example.annaqah.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private var selectedSize: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val imageUrl = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")

        // Set data to views
        Glide.with(this).load(imageUrl).into(binding.itemImage)
        binding.textView11.text = name
        binding.price.text = price

        val buttons = listOf(binding.buttonS, binding.buttonM, binding.buttonL, binding.buttonXL)

        buttons.forEach { button ->
            button.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    buttons.filter { it != button }.forEach { it.isChecked = false }
                    selectedSize = button.text.toString()
                } else if (selectedSize == button.text.toString()) {
                    selectedSize = null
                }
            }
        }

        binding.proceedButton.setOnClickListener {
            if (selectedSize != null) {
                val confirmedData = ConfirmedData(
                    confirmedImage = imageUrl ?: "",
                    confirmedItemName = name ?: "",
                    confirmedPrice = price ?: "",
                    confirmedSize = selectedSize!!
                )
                val intent = Intent(this, ProceedActivity::class.java).apply {
                    putExtra("CONFIRMED_DATA", confirmedData)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select a size", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
