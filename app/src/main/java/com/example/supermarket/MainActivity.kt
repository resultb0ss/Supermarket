package com.example.supermarket

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supermarket.databinding.ActivityMainBinding
import com.example.supermarket.databinding.ActivitySecondBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainActivityButtonBTN.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java )
            startActivity(intent)
        }


    }
}