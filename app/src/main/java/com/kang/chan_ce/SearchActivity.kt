package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kang.chan_ce.databinding.ActivityMainBinding
import com.kang.chan_ce.databinding.ActivitySearchBinding

class SearchActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStore1.setOnClickListener {
            val intent = Intent(this, StoreDetailActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}