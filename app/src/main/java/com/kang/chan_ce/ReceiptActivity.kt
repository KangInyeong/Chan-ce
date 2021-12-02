package com.kang.chan_ce

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kang.chan_ce.databinding.ActivityReceiptBinding
import com.kang.chan_ce.databinding.ActivityStoreDetailBinding
import kotlinx.android.synthetic.main.activity_receipt.*

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

        val menu = intent.getStringExtra("storeMenu")
        val menu1 = intent.getStringExtra("storeMenu1")
        val menu2 = intent.getStringExtra("storeMenu2")

        val nmenu = intent.getStringExtra("kimchi").toString()
        val nmenu1 = intent.getStringExtra("jinmichae").toString()
        val nmenu2 = intent.getStringExtra("mumalange").toString()

        var allmenu : String

        if(nmenu != "0" && nmenu1 != "0" && nmenu2 != "0"){
            binding.txtmenu.setText(menu)
            binding.txtmenu1.setText(menu1)
            binding.txtmenu2.setText(menu2)

            binding.numKimchi.setText(nmenu)
            binding.numJinMiChae.setText(nmenu1)
            binding.numMuMaLangE.setText(nmenu2)

            allmenu = "$menu X$nmenu / $menu1 X$nmenu1 / $menu2 X$nmenu2"
        }else if(nmenu != "0" && nmenu1 != "0" && nmenu2 == "0"){
            binding.txtmenu.setText(menu)
            binding.txtmenu1.setText(menu1)
            binding.txtmenu2.setText("")

            binding.numKimchi.setText(nmenu)
            binding.numJinMiChae.setText(nmenu1)
            binding.numMuMaLangE.setText("")

            allmenu = "$menu X$nmenu / $menu1 X$nmenu1 / $menu2 X$nmenu2"

        }else if(nmenu != "0" && nmenu1 == "0" && nmenu2 != "0"){
            binding.txtmenu.setText(menu)
            binding.txtmenu1.setText(menu2)
            binding.txtmenu2.setText("")

            binding.numKimchi.setText(nmenu)
            binding.numJinMiChae.setText(nmenu2)
            binding.numMuMaLangE.setText("")

            allmenu = "$menu X$nmenu / $menu1 X$nmenu1 / $menu2 X$nmenu2"
        }else if(nmenu == "0" && nmenu1 != "0" && nmenu2 != "0"){
            binding.txtmenu.setText(menu1)
            binding.txtmenu1.setText(menu2)
            binding.txtmenu2.setText("")

            binding.numKimchi.setText(nmenu1)
            binding.numJinMiChae.setText(nmenu2)
            binding.numMuMaLangE.setText("")

            allmenu = "$menu X$nmenu / $menu1 X$nmenu1 / $menu2 X$nmenu2"
        }else if(nmenu == "0" && nmenu1 == "0" && nmenu2 != "0"){
            binding.txtmenu.setText(menu2)
            binding.txtmenu1.setText("")
            binding.txtmenu2.setText("")

            binding.numKimchi.setText(nmenu2)
            binding.numJinMiChae.setText("")
            binding.numMuMaLangE.setText("")

            allmenu = "$menu X$nmenu / $menu1 X$nmenu1 / $menu2 X$nmenu2"
        }else if(nmenu == "0" && nmenu1 != "0" && nmenu2 == "0"){
            binding.txtmenu.setText(menu1)
            binding.txtmenu1.setText("")
            binding.txtmenu2.setText("")

            binding.numKimchi.setText(nmenu1)
            binding.numJinMiChae.setText("")
            binding.numMuMaLangE.setText("")

            allmenu = "$menu X$nmenu / $menu1 X$nmenu1 / $menu2 X$nmenu2"
        }else if(nmenu != "0" && nmenu1 == "0" && nmenu2 == "0"){
            binding.txtmenu.setText(menu)
            binding.txtmenu1.setText("")
            binding.txtmenu2.setText("")

            binding.numKimchi.setText(nmenu)
            binding.numJinMiChae.setText("")
            binding.numMuMaLangE.setText("")

            allmenu = "$menu X$nmenu / $menu1 X$nmenu1 / $menu2 X$nmenu2"
        }else{
            //set 0,,,? maybe
            binding.txtmenu.setText(menu)
            binding.txtmenu1.setText(menu1)
            binding.txtmenu2.setText(menu2)

            binding.numKimchi.setText(nmenu)
            binding.numJinMiChae.setText(nmenu1)
            binding.numMuMaLangE.setText(nmenu2)

            allmenu = "$menu X$nmenu / $menu1 X$nmenu1 / $menu2 X$nmenu2"
        }



        var totalCost = intent.getStringExtra("total cost").toString()
        binding.btnTotal.setText(totalCost)

        var week = intent.getStringExtra("week")
        binding.btnWeek.setText(week)

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

        var check = false

        binding.btnDone.setOnClickListener {
            if(!check){
                Toast.makeText(this, "You have to check the agreement!", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, AccountActivity::class.java).apply {
                    putExtra("total cost",totalCost.toString())

                    val uid = uid.toString()
                    myRef.child(uid).setValue(User(store, week, days, totalCost, allmenu))
                }
                startActivity(intent)
            }

        }

        btnCheckBox.setOnClickListener{
            AlertDialog.Builder(this)
                .setTitle("Agreement")
                .setMessage("Are you sure this receipt's information?")
                .setPositiveButton("Sure", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(this, "Complete Agreement", Toast.LENGTH_SHORT).show()
                    check = true
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(this, "Not Agreement", Toast.LENGTH_SHORT).show()
                    check = false
                })
                .show()
        }


        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}