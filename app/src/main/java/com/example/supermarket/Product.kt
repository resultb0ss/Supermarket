package com.example.supermarket

import java.io.Serializable

class Product(
    var name: String?,
    var price: String?,
    var description: String?,
    var images: String?
): Serializable{
}