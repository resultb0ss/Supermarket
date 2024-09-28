package com.example.supermarket

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ListAdapter (context: Context, products: MutableList<Product>):
    ArrayAdapter<Product>(context,R.layout.item_list, products) {


    @SuppressLint("CutPasteId")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        var view = view
        val product = getItem(position)
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false)
        }



        val imageProductTV = view?.findViewById<ImageView>(R.id.itemImageProductImageViewIV)
        val nameProductTV = view?.findViewById<TextView>(R.id.itemListViewNameLV)
        val priceProductTV = view?.findViewById<TextView>(R.id.itemListViewPriceLV)

        imageProductTV?.setImageBitmap(product?.images)
        nameProductTV?.text = product?.name
        priceProductTV?.text = product?.price

        return view!!
    }
}