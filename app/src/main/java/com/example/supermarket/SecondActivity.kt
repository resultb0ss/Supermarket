package com.example.supermarket

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supermarket.databinding.ActivitySecondBinding
import java.io.IOException

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private val products: MutableList<Product> = mutableListOf()
    var bitmap: Bitmap? = null
    val GALLERY_REQUEST = 302


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listAdapter = ListAdapter(this@SecondActivity, products)
        binding.secondActivityMainListVewLV.adapter = listAdapter


        binding.secondActivityAddButtonBTN.setOnClickListener{
            val name = binding.secondActivityNameEditTextET.text.toString()
            val price = binding.secondActivityPriceEditTextET.text.toString()
            val image = bitmap
            val product = Product(name,price,image)
            products.add(product)
            listAdapter.notifyDataSetChanged()

            binding.secondActivityNameEditTextET.text.clear()
            binding.secondActivityPriceEditTextET.text.clear()
        }

        binding.secondActivityImageProductImageViewIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent,GALLERY_REQUEST)
        }

        binding.secondActivityExitButtonBTN.setOnClickListener {
            this.finishAffinity()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GALLERY_REQUEST -> if(resultCode == RESULT_OK){
                val selectedImage: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException){
                    e.printStackTrace()
                }
                binding.secondActivityImageProductImageViewIV.setImageURI(selectedImage)
            }

        }
    }
}