package com.kang.chan_ce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kang.chan_ce.databinding.ActivityMoreBinding

class MoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMoreBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        val userid = uid.toString()

        myRef.child(userid).child("storeName").get().addOnSuccessListener {
            val value1 = it.value.toString()
            binding.txtStore.text = value1
        }

        myRef.child(userid).child("subWeek").get().addOnSuccessListener {
            val value2 = it.value.toString()
            binding.txtweek.text = value2
        }

        myRef.child(userid).child("pickDay").get().addOnSuccessListener {
            val value3 = it.value.toString()
            binding.txtDay.text = value3
        }

        myRef.child(userid).child("menu").get().addOnSuccessListener {
            val allmenu = it.value.toString()
            val menulist = allmenu.split(" / ")

            val menu = menulist[0].split(" X")
            val menu1 = menulist[1].split(" X")
            val menu2 = menulist[2].split(" X")

            if(menu[1] != "0" && menu1[1] != "0" && menu2[1] != "0"){
                binding.txtmenu.text = menulist[0]
                binding.txtmenu1.text = menulist[1]
                binding.txtmenu2.text = menulist[2]
            }else if(menu[1] != "0" && menu1[1] != "0" && menu2[1] == "0"){
                binding.txtmenu.text = menulist[0]
                binding.txtmenu1.text = menulist[1]
                binding.txtmenu2.text = ""
            }else if(menu[1] != "0" && menu1[1] == "0" && menu2[1] != "0"){
                binding.txtmenu.text = menulist[0]
                binding.txtmenu1.text = menulist[2]
                binding.txtmenu2.text = ""
            }else if(menu[1] == "0" && menu1[1] != "0" && menu2[1] != "0"){
                binding.txtmenu.text = menulist[1]
                binding.txtmenu1.text = menulist[2]
                binding.txtmenu2.text = ""
            }else if(menu[1] == "0" && menu1[1] == "0" && menu2[1] != "0"){
                binding.txtmenu.text = menulist[2]
                binding.txtmenu1.text = ""
                binding.txtmenu2.text = ""
            }else if(menu[1] == "0" && menu1[1] != "0" && menu2[1] == "0"){
                binding.txtmenu.text = menulist[1]
                binding.txtmenu1.text = ""
                binding.txtmenu2.text = ""
            }else if(menu[1] != "0" && menu1[1] == "0" && menu2[1] == "0"){
                binding.txtmenu.text = menulist[0]
                binding.txtmenu1.text = ""
                binding.txtmenu2.text = ""
            }else{
                //set 0 ...
                binding.txtmenu.text = menulist[0]
                binding.txtmenu1.text = menulist[1]
                binding.txtmenu2.text = menulist[2]
            }

        }

        binding.btnMyPage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

    }
}