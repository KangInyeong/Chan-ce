package com.kang.chan_ce

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kang.chan_ce.databinding.ActivityMypageBinding
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ChildEventListener
import kotlinx.android.synthetic.main.activity_mypage.*
import org.w3c.dom.Comment


class MypageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val useremail = user?.email
        val uid = user?.uid
        val userid = uid.toString()
        var username = user?.displayName

        if(username == ""||username == null){
            val email_list = useremail?.split("@")
            if (username != null) {
                username = email_list!![0]
            }
        }

        binding.userName.setText(username)
        binding.txtStore.setText("Please subscription!")
        binding.txtweek.setText("")
        binding.btnMore.setVisibility(View.INVISIBLE)
        binding.txtdeposit.setVisibility(View.INVISIBLE)
        binding.txtone.setVisibility(View.INVISIBLE)

        var store :String = ""


        //데이터 변화가 확인되면 파베에서, 그대에 구독정보 보이기
        myRef.child(userid).child("storeName").get().addOnSuccessListener {
            store = it.value.toString()
            if(store != null && store != "" && store != "null"){
                binding.txtStore.text = store
                binding.btnMore.setVisibility(View.VISIBLE)
                binding.txtdeposit.setVisibility(View.VISIBLE)
                binding.txtone.setVisibility(View.VISIBLE)
            }
        }
            .addOnFailureListener {
                binding.txtStore.text = "Please subscription!"
            }

        myRef.child(userid).child("subWeek").get().addOnSuccessListener {
            val value2 = it.value.toString()
            if (value2 != null && value2 != "" && value2 != "null"){
                binding.txtweek.text = value2
                binding.btnMore.setVisibility(View.VISIBLE)
                binding.txtdeposit.setVisibility(View.VISIBLE)
                binding.txtone.setVisibility(View.VISIBLE)
            }else{
                binding.txtweek.setText("")
            }
        }.addOnFailureListener {
            binding.txtweek.text = ""
        }

        binding.btnMainPage.setOnClickListener {
            Toast.makeText(this,"$userid // $useremail // $userid", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnQr1.setOnClickListener {
            val intent = Intent(this, QrActivity::class.java).apply {
                putExtra("userName", username)
            }
            startActivity(intent)
        }

        binding.btnQr2.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java).apply {
                putExtra("userName", username)
            }
            startActivity(intent)
        }

        binding.btnMore.setOnClickListener {
            val intent = Intent(this, MoreActivity::class.java).apply {
                putExtra("storename",store)
            }
            startActivity(intent)
        }

//        binding.btnMore1.setOnClickListener {
//            val intent = Intent(this, MoreActivity::class.java)
//            startActivity(intent)
//        }

        binding.btnMainPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this,"signed out", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

}