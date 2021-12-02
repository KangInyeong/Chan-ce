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

        var check = false

        binding.btnDone.setOnClickListener {
            if(!check){
                Toast.makeText(this, "You have to check the agreement!", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, AccountActivity::class.java).apply {
                    putExtra("total cost",totalCost.toString())

                    val uid = uid.toString()
                    myRef.child(uid).setValue(User(store, totalCost))
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