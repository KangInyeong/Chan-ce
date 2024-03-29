package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kang.chan_ce.databinding.ActivityAccountBinding
import com.kang.chan_ce.databinding.ActivitySearchBinding
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()

//        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
//        val uid = user?.uid
//        val useremail = user?.email
//
//        var username = user?.displayName
//
//        if (uid != null) {
//            myRef.child(uid).child("userName").get().addOnSuccessListener {
//                binding.userName.setText("${it.value}")
//
//            }.addOnFailureListener{
//                Log.e("firebase", "Error getting data", it)
//            }
//        }

        binding.userName.setText(intent.getStringExtra("userName").toString())


        binding.btnTotal.setText(intent.getStringExtra("total cost").toString())

        //여기서는 뒤로가기가 안되어야할듯
        btnBack.visibility = View.INVISIBLE

        binding.btnBackMainPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnBackMyPage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }


    }
}