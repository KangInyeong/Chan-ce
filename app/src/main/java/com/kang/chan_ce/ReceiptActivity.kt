package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kang.chan_ce.databinding.ActivityReceiptBinding
import com.kang.chan_ce.databinding.ActivityStoreDetailBinding

class ReceiptActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()

        binding.storeName.setText(intent.getStringExtra("storeName"))
        binding.txtmenu.setText(intent.getStringExtra("storeMenu"))
        binding.txtmenu1.setText(intent.getStringExtra("storeMenu1"))
        binding.txtmenu2.setText(intent.getStringExtra("storeMenu2"))

        binding.numKimchi.setText(intent.getStringExtra("kimchi").toString())
        binding.numJinMiChae.setText(intent.getStringExtra("jinmichae").toString())
        binding.numMuMaLangE.setText(intent.getStringExtra("mumalange").toString())

        var totalCost = intent.getStringExtra("total cost").toString()
        binding.btnTotal.setText(totalCost)

        var mon = intent.getStringExtra("mon").toString()
        var tue = intent.getStringExtra("tue").toString()
        var wed = intent.getStringExtra("wed").toString()
        var thu = intent.getStringExtra("thu").toString()
        var fri = intent.getStringExtra("fri").toString()
        var sat = intent.getStringExtra("sat").toString()
        var sun = intent.getStringExtra("sun").toString()

        var list = arrayListOf(mon,tue,wed,thu,fri,sat,sun)
        var days =""

        for (i in list)
            if (i != "") {
                days += i+" "
            }else{
                continue
            }

        binding.btnDay.setText(days)

        val store = intent.getStringExtra("storeName")

        binding.btnDone.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java).apply {
                putExtra("total cost",totalCost.toString())

                val uid = uid.toString()
                myRef.child(uid).setValue(User(store, totalCost))
            }
            startActivity(intent)
        }


        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}