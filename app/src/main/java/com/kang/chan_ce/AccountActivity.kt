package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kang.chan_ce.databinding.ActivityAccountBinding
import com.kang.chan_ce.databinding.ActivitySearchBinding

class AccountActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid
        val useremail = user?.email

        var username = user?.displayName

        if(username == ""){
            val email_list = useremail?.split("@")
            if (username != null) {
                username = email_list!![0]
            }
        }else if(username == null){
            username = "kiyoog02"
        }

        binding.userName.setText(username)

        binding.btnTotal.setText(intent.getStringExtra("total cost").toString())

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