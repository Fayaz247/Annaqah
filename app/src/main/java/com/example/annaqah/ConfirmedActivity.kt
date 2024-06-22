package com.example.annaqah

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.annaqah.databinding.ActivityConfirmedBinding
import com.example.annaqah.databinding.ActivityStartBinding

class ConfirmedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.ordersButton.setOnClickListener {
            val intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
        }




    }
}