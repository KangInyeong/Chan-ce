package com.kang.chan_ce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kang.chan_ce.databinding.ActivityMypageBinding
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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

        if(username == ""){
            val email_list = useremail?.split("@")
            if (username != null) {
                username = email_list!![0]
            }
        }else if(username == null){
            username = "kiyoog02"
        }
        Log.e("이메일 로그인", "$useremail $username")

        binding.userName.text = username

        var store :String = ""

        myRef.child(userid).child("storeName").get().addOnSuccessListener {
            store = it.value.toString()
            binding.txtStore.text = store
        }

        myRef.child(userid).child("subWeek").get().addOnSuccessListener {
            val value2 = it.value.toString()
            binding.txtweek.text = value2
        }

        binding.btnMainPage.setOnClickListener {
            Toast.makeText(this,"$userid // $useremail // $userid", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnQr1.setOnClickListener {
            val intent = Intent(this, QrActivity::class.java)
            startActivity(intent)
        }

        binding.btnQr2.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
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