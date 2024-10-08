package com.example.supermarket

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supermarket.databinding.ActivityDetailsBinding
import com.example.supermarket.databinding.ItemListBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailsActivityExitButtonBTN.setOnClickListener{
            finishAffinity()
        }

        val product: Product = intent.extras?.getSerializable("product") as Product
//        val products = intent.getSerializableExtra("products")
//        val item = intent.extras?.getInt("position")
//        var check = intent.extras?.getBoolean("check")


        val name = product.name
        val price = product.price
        val description = product.description
        val image: Uri? = Uri.parse(product.images)

        binding.detailsActivityNameProductTV.text = name
        binding.detailsActivityPriceProductTV.text = price
        binding.detailsActivityImageProductIV.setImageURI(image)
        binding.detailsActivityDescriptionProductTV.text = description

    }
}