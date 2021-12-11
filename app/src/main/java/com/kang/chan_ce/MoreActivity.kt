package com.kang.chan_ce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kang.chan_ce.databinding.ActivityMoreBinding
import kotlinx.android.synthetic.main.activity_more.*

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

        val key = intent.getStringExtra("infokey")

        if (key != null) {
            myRef.child(userid).child(key).child("storeName").get().addOnSuccessListener {
                val value1 = it.value.toString()
                binding.txtStore.text = value1
            }
        }

        if (key != null) {
            myRef.child(userid).child(key).child("subWeek").get().addOnSuccessListener {
                val value2 = it.value.toString()
                binding.txtweek.text = value2
            }
        }

        if (key != null) {
            myRef.child(userid).child(key).child("pickDay").get().addOnSuccessListener {
                val value3 = it.value.toString()
                binding.txtDay.text = value3
            }
        }

        if (key != null) {
            myRef.child(userid).child(key).child("menu").get().addOnSuccessListener {
                val allmenu = it.value.toString()
                val menulist = allmenu.split(" / ")

                val menu = menulist[0].split(" X")
                val menu1 = menulist[1].split(" X")
                val menu2 = menulist[2].split(" X")

                val num = menu[1]
                val num1 = menu1[1]
                val num2 = menu2[1]

                if(menu[1] != "0" && menu1[1] != "0" && menu2[1] != "0"){
                    binding.txtmenu.text = menulist[0]
                    binding.txtmenu1.text = menulist[1]
                    binding.txtmenu2.text = menulist[2]
                    binding.numKimchi.text = num
                    binding.numJinMiChae.text = num1
                    binding.numMuMaLangE.text = num2
                }else if(menu[1] != "0" && menu1[1] != "0" && menu2[1] == "0"){
                    binding.txtmenu.text = menulist[0]
                    binding.txtmenu1.text = menulist[1]
                    binding.txtmenu2.text = ""
                    binding.numKimchi.text = num
                    binding.numJinMiChae.text = num1
                    binding.numMuMaLangE.text = ""
                }else if(menu[1] != "0" && menu1[1] == "0" && menu2[1] != "0"){
                    binding.txtmenu.text = menulist[0]
                    binding.txtmenu1.text = menulist[2]
                    binding.txtmenu2.text = ""
                    binding.numKimchi.text = num
                    binding.numJinMiChae.text = num1
                    binding.numMuMaLangE.text = ""
                }else if(menu[1] == "0" && menu1[1] != "0" && menu2[1] != "0"){
                    binding.txtmenu.text = menulist[1]
                    binding.txtmenu1.text = menulist[2]
                    binding.txtmenu2.text = ""
                    binding.numKimchi.text = num
                    binding.numJinMiChae.text = num1
                    binding.numMuMaLangE.text = ""
                }else if(menu[1] == "0" && menu1[1] == "0" && menu2[1] != "0"){
                    binding.txtmenu.text = menulist[2]
                    binding.txtmenu1.text = ""
                    binding.txtmenu2.text = ""
                    binding.numKimchi.text = num
                    binding.numJinMiChae.text = ""
                    binding.numMuMaLangE.text = ""
                }else if(menu[1] == "0" && menu1[1] != "0" && menu2[1] == "0"){
                    binding.txtmenu.text = menulist[1]
                    binding.txtmenu1.text = ""
                    binding.txtmenu2.text = ""
                    binding.numKimchi.text = num
                    binding.numJinMiChae.text = ""
                    binding.numMuMaLangE.text = ""
                }else if(menu[1] != "0" && menu1[1] == "0" && menu2[1] == "0"){
                    binding.txtmenu.text = menulist[0]
                    binding.txtmenu1.text = ""
                    binding.txtmenu2.text = ""
                    binding.numKimchi.text = num
                    binding.numJinMiChae.text = ""
                    binding.numMuMaLangE.text = ""
                }else{
                    //set 0 ...
                    binding.txtmenu.text = menulist[0]
                    binding.txtmenu1.text = menulist[1]
                    binding.txtmenu2.text = menulist[2]
                    binding.numKimchi.text = ""
                    binding.numJinMiChae.text = ""
                    binding.numMuMaLangE.text = ""
                }

            }
        }

        binding.btnMyPage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

    }
}