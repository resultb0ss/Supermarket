package com.example.supermarket

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supermarket.databinding.ActivityDetailsBinding
import com.example.supermarket.databinding.ItemListBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val GALLERY_REQUEST = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Set detail info about product
        val product: Product = intent.extras?.getSerializable("product") as Product
        val products = intent.getSerializableExtra("products")
        val item = intent.extras?.getInt("position")
        var check = intent.extras?.getBoolean("check")


        val name = product.name
        val price = product.price
        val description = product.description
        val image: Uri? = Uri.parse(product.images)

        binding.detailsActivityNameProductTV.text = name
        binding.detailsActivityPriceProductTV.text = "${product?.price} руб."
        binding.detailsActivityImageProductIV.setImageURI(image)
        binding.detailsActivityDescriptionProductTV.text = description

        //Exit button on toolbar
        binding.detailsActivityExitButtonBTN.setOnClickListener{
            Toast.makeText(this,"Программа завершена", Toast.LENGTH_LONG).show()
            this.finishAffinity()
        }

        //Back button on toolbar
        binding.detailsActivityBackButtonBTN.setOnClickListener{

            val product = Product(
                binding.detailsActivityNameProductTV.text.toString(),
                binding.detailsActivityPriceProductTV.text.toString(),
                binding.detailsActivityDescriptionProductTV.text.toString(),
                product.images// ?? Вот тут вопрос? понимаю что старое изображение отправляется
            )

            val list: MutableList<Product> = products as MutableList<Product>
            if (item != null) {
                swap(item,product,products)
            }
            check = false
            val intent = Intent(this,SecondActivity::class.java)
            intent.putExtra("list",list as ArrayList<Product>)
            intent.putExtra("newCheck",check)
            startActivity(intent)
            finish()
        }


        binding.detailsActivityImageProductIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent,GALLERY_REQUEST)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GALLERY_REQUEST -> if(resultCode == RESULT_OK){
                val photoUri = data?.data
                binding.detailsActivityImageProductIV.setImageURI(photoUri)
            }

        }
    }

    private fun swap(item: Int, product: Product, products: MutableList<Product>){
        products.add(item + 1,product)
        products.removeAt(item)
    }
}