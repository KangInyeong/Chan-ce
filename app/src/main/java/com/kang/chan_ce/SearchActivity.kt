package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kang.chan_ce.databinding.ActivityMainBinding
import com.kang.chan_ce.databinding.ActivitySearchBinding

class SearchActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}