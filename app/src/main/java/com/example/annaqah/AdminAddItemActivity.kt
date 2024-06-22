package com.example.annaqah

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.annaqah.data.ItemData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AdminAddItemActivity : AppCompatActivity() {

    private lateinit var itemTypeSpinner: Spinner
    private lateinit var itemNameEditText: EditText
    private lateinit var itemPriceEditText: EditText
    private lateinit var itemImageView: ImageView
    private lateinit var selectImageButton: Button
    private lateinit var addItemButton: Button

    private var selectedImageUri: Uri? = null

    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_item)

        itemTypeSpinner = findViewById(R.id.itemTypeSpinner)
        itemNameEditText = findViewById(R.id.itemNameEditText)
        itemPriceEditText = findViewById(R.id.itemPriceEditText)
        itemImageView = findViewById(R.id.itemImageView)
        selectImageButton = findViewById(R.id.selectImageButton)
        addItemButton = findViewById(R.id.addItemButton)

        database = FirebaseDatabase.getInstance().getReference("shopItem")
        storage = FirebaseStorage.getInstance()

        // Set up the spinner with item types
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.item_types,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        itemTypeSpinner.adapter = adapter

        // Set up the image selector
        selectImageButton.setOnClickListener {
            pickImage.launch("image/*")
        }

        addItemButton.setOnClickListener {
            val itemType = itemTypeSpinner.selectedItem.toString()
            val itemName = itemNameEditText.text.toString().trim()
            val itemPrice = itemPriceEditText.text.toString().trim()

            if (selectedImageUri != null && itemName.isNotEmpty() && itemPrice.isNotEmpty()) {
                uploadItem(itemType, itemName, itemPrice)
            } else {
                Toast.makeText(this, "Please fill all the fields and select an image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            // Use Glide to load the image into ImageView
            Glide.with(this)
                .load(uri)
                .into(itemImageView)
        }
    }

    private fun uploadItem(itemType: String, itemName: String, itemPrice: String) {
        val storageRef = storage.reference.child("itemImages/${UUID.randomUUID()}.jpg")
        selectedImageUri?.let { uri ->
            storageRef.putFile(uri).addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val itemId = database.push().key
                    if (itemId != null) {
                        val item = ItemData(downloadUrl.toString(), itemName, itemPrice)
                        database.child(itemType).child(itemId).setValue(item).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                val errorMessage = task.exception?.message ?: "Unknown error"
                                Toast.makeText(this, "Failed to add item: $errorMessage", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            } .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
