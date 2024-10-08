package com.example.supermarket

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.supermarket.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(), Removable, Updatable{

    private lateinit var binding: ActivitySecondBinding
    private var products: MutableList<Product> = mutableListOf()

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
            listAdapter?.notifyDataSetChanged()
            clearEditFields()
            photoUri = null
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
            Toast.makeText(this,"Программа завершена", Toast.LENGTH_LONG).show()
            this.finishAffinity()
        }

        binding.secondActivityBackButtonBTN.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun clearEditFields() {
        binding.secondActivityNameEditTextET.text.clear()
        binding.secondActivityPriceEditTextET.text.clear()
        binding.secondActivityDescriptionEditTextET.text.clear()
        binding.secondActivityImageProductImageViewIV.setImageResource(R.drawable.baseline_person_24)
    }

    override fun onResume() {
        super.onResume()
        check = intent.extras?.getBoolean("newCheck") ?: true
        if (!check) {
            products = intent.getSerializableExtra("list") as MutableList<Product>
            listAdapter = ListAdapter(this, products)
            check = true

        }
        binding.secondActivityMainListVewLV.adapter = listAdapter
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