package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.kang.chan_ce.databinding.ActivityStoreDetailBinding

class StoreDetailActivity: AppCompatActivity() {

    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

//        storedata = intent.getSerializableExtra("data") as StoreData

//        Glide.with(this).load(storedata.storeImage).into(storeImage)
//        storeName.text = storedata.storeName

        val url = intent.getStringExtra("StoreImage")

        Glide.with(binding.storeImage).load(url).into(binding.storeImage)
        binding.storeName.setText(intent.getStringExtra("StoreName"))
        binding.storeLocation.setText(intent.getStringExtra("StoreLocation"))
        binding.storeTime.setText(intent.getStringExtra("StoreTime"))

        binding.btnSelect.setOnClickListener {
//            Log.e("안녕 여기 로그좀","클릭")
            val intent = Intent(this, SubscriptionActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}