package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kang.chan_ce.databinding.ActivitySearchBinding
import com.kang.chan_ce.databinding.ActivityStoreDetailBinding

class StoreDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelect.setOnClickListener {
            val intent = Intent(this, SubscriptionActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}