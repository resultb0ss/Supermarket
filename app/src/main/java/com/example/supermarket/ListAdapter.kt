package com.example.supermarket

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.supermarket.databinding.ItemListBinding

class ListAdapter (context: Context, products: MutableList<Product>):
    ArrayAdapter<Product>(context,R.layout.item_list, products) {

        private lateinit var binding: ItemListBinding

    @SuppressLint("CutPasteId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val product = getItem(position)
        binding = if (convertView == null) {
            ItemListBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemListBinding.bind(convertView)
        }


        binding.itemImageProductImageViewIV.setImageURI(Uri.parse(product?.images))
        binding.itemListViewNameLV.text = product?.name
        binding.itemListViewPriceLV.text = "${product?.price} руб."

        return binding.root
    }
}