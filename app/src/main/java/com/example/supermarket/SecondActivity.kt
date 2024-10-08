package com.example.supermarket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.supermarket.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(), Removable, Updatable{

    private lateinit var binding: ActivitySecondBinding
    private val products: MutableList<Product> = mutableListOf()

    var listAdapter: ListAdapter? = null
    var photoUri: Uri? = null
    val GALLERY_REQUEST = 302
    var item: Int? = null
    var product: Product? = null
    var check = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listAdapter = ListAdapter(this@SecondActivity, products)
        binding.secondActivityMainListVewLV.adapter = listAdapter


        binding.secondActivityAddButtonBTN.setOnClickListener{
            val name = binding.secondActivityNameEditTextET.text.toString()
            val price = binding.secondActivityPriceEditTextET.text.toString()
            val image = photoUri.toString()
            val description = binding.secondActivityDescriptionEditTextET.text.toString()
            val product = Product(name,price,description,image)
            products.add(product!!)
            photoUri = null
            //Clear edit field не дает сделать
            listAdapter?.notifyDataSetChanged()


            binding.secondActivityNameEditTextET.text.clear()
            binding.secondActivityPriceEditTextET.text.clear()
            binding.secondActivityDescriptionEditTextET.text.clear()
        }

        binding.secondActivityMainListVewLV.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, view, position, id ->
                val product = listAdapter!!.getItem(position)
                item = position
                val dialog = MyAlertDialog()
                val args = Bundle()
                args.putSerializable("product", product)
                dialog.arguments = args
                dialog.show(supportFragmentManager,"custom")
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
                photoUri = data?.data
                binding.secondActivityImageProductImageViewIV.setImageURI(photoUri)
            }

        }
    }

    override fun remove(product: Product) {
        listAdapter?.remove(product)
    }

    override fun update(product: Product) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("product",product)
        intent.putExtra("products", this.products as ArrayList<Product>)
        intent.putExtra("position", item)
        intent.putExtra("check",check)
        startActivity(intent)
    }
}