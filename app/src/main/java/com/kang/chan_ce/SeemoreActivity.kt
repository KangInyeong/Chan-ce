package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.kang.chan_ce.databinding.ActivityMypageBinding
import com.kang.chan_ce.databinding.ActivitySeemoreBinding
import kotlinx.android.synthetic.main.activity_seemore.*

class SeemoreActivity : AppCompatActivity() {

    var storeName = mutableListOf<String>() // user의 구독 날짜
    //가져와야 하는 값 #2 구독 요일
    var storeWeek = mutableListOf<String>()// user의 픽업 요일

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        val userid = user?.uid.toString()

        val binding = ActivitySeemoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnMainPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        val keyList = mutableListOf<String>()
        val userList = mutableListOf<Reserv>()
        var size = 0

        myRef.child(userid).get().addOnSuccessListener {
            myRef.child(userid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        Log.e("정보", "$snapshot")
                        var key = snapshot.key.toString()
                        keyList.add(key)
                        var user = snapshot.getValue<Reserv>()
                        if (user != null) {
                            userList.add(user)
                        }
                    }

                    size = userList.size
                    Log.e("정보","$userList")

                    for(i in 1 until size step 1){
                        storeName.add(userList[i].storeName.toString())
                        storeWeek.add(userList[i].subWeek.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }


    }


}