package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kang.chan_ce.databinding.ActivitySearchBinding
import com.kang.chan_ce.databinding.ActivityStoreDetailBinding

class StoreDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent

        binding.storeName.setText(intent.getStringExtra("name").toString())
        binding.storeLocation.setText(intent.getStringExtra("location").toString());
        binding.storeName.setText(intent.getStringExtra("time").toString());

        binding.btnSelect.setOnClickListener {
            val intent = Intent(this, SubscriptionActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}