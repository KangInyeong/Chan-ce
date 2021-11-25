package com.kang.chan_ce

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kang.chan_ce.databinding.ActivitySubscriptionBinding
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SubscriptionActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference()
        val store = intent.getStringExtra("StoreName")
        val menu = intent.getStringExtra("StoreMenu")
        val menu1 = intent.getStringExtra("StoreMenu1")
        val menu2 = intent.getStringExtra("StoreMenu2")

        binding.txtmenu.text = menu
        binding.txtmenu1.text = menu1
        binding.txtmenu2.text = menu2


        //spinner
        val spinner: Spinner = binding.spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.date,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        var numDay = 0
        var mon = ""
        var tue = ""
        var wed = ""
        var thu = ""
        var fri = ""
        var sat = ""
        var sun = ""

        // plus & minus
        var count1 = 0
        var count2 = 0
        var count3 = 0

        var numKimchi = 0
        var numJinmichae = 0
        var numMumalange = 0

        //temp banchan price
        var kimchi = 1000
        var jinmichae = 2000
        var mumalange = 1500

        var totalCost = 0

        //select day
        binding.btnMON.setOnClickListener {
            binding.btnMON?.isSelected = binding.btnMON.isSelected != true
            if (binding.btnMON.isSelected == true){
                numDay++
                mon = "MON"
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
            else{
                numDay--
                mon = ""
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
        }

        binding.btnTUE.setOnClickListener {
            binding.btnTUE?.isSelected = binding.btnTUE.isSelected != true
            if (binding.btnTUE.isSelected == true){
                numDay++
                tue = "TUE"
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
            else{
                numDay--
                tue = ""
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
        }

        binding.btnWED.setOnClickListener {
            binding.btnWED?.isSelected = binding.btnWED.isSelected != true
            if (binding.btnWED.isSelected == true){
                numDay++
                wed = "WED"
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
            else{
                numDay--
                wed = ""
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
        }

        binding.btnTHU.setOnClickListener {
            binding.btnTHU?.isSelected = binding.btnTHU.isSelected != true
            if (binding.btnTHU.isSelected == true){
                numDay++
                thu= "THU"
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
            else{
                numDay--
                thu = ""
                binding.btnTotal.setText((numDay*totalCost).toString())
            }

        }

        binding.btnFRI.setOnClickListener {
            binding.btnFRI?.isSelected = binding.btnFRI.isSelected != true
            if (binding.btnFRI.isSelected == true){
                numDay++
                fri = "FRI"
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
            else{
                numDay--
                fri = ""
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
        }

        binding.btnSAT.setOnClickListener {
            binding.btnSAT?.isSelected = binding.btnSAT.isSelected != true
            if (binding.btnSAT.isSelected == true){
                numDay++
                sat = "SAT"
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
            else{
                numDay--
                sat = ""
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
        }

        binding.btnSUN.setOnClickListener {
            binding.btnSUN?.isSelected = binding.btnSUN.isSelected != true
            if (binding.btnSUN.isSelected == true){
                numDay++
                sun = "SUN"
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
            else{
                numDay--
                sun = ""
                binding.btnTotal.setText((numDay*totalCost).toString())
            }
        }

        //banchan num count

        binding.btnAdd1.setOnClickListener {
            count1++
            numKimchi++
            totalCost+=kimchi
            binding.btnCount1.setText(count1.toString())
            binding.btnTotal.setText((numDay*totalCost).toString())
        }

        binding.btnAdd2.setOnClickListener {
            count2++
            numJinmichae++
            totalCost+=jinmichae
            binding.btnCount2.setText(count2.toString())
            binding.btnTotal.setText((numDay*totalCost).toString())
        }

        binding.btnAdd3.setOnClickListener {
            count3++
            numMumalange++
            totalCost+=mumalange
            binding.btnCount3.setText(count3.toString())
            binding.btnTotal.setText((numDay*totalCost).toString())
        }

        binding.btnMinus1.setOnClickListener {
            if (count1 > 0) {
                count1--
                numKimchi--
                totalCost-=kimchi
                binding.btnCount1.setText(count1.toString())
                binding.btnTotal.setText((numDay*totalCost).toString())
            }else {}
        }

        binding.btnMinus2.setOnClickListener {
            if (count2 > 0) {
                count2--
                numJinmichae--
                totalCost-=jinmichae
                binding.btnCount2.setText(count2.toString())
                binding.btnTotal.setText((numDay*totalCost).toString())
            }else {}
        }

        binding.btnMinus3.setOnClickListener {
            if (count3 > 0) {
                count3--
                numMumalange--
                totalCost-=mumalange
                binding.btnCount3.setText(count3.toString())
                binding.btnTotal.setText((numDay*totalCost).toString())
            }else {}
        }

        binding.btnDone.setOnClickListener {
            val intent = Intent(this, ReceiptActivity::class.java).apply {

                putExtra("storeName",store)
                Log.v("가게11","$store")
                putExtra("storeMenu",menu.toString())
                putExtra("storeMenu1",menu1.toString())
                putExtra("storeMenu2",menu2.toString())

                putExtra("kimchi",numKimchi.toString())
                putExtra("jinmichae",numJinmichae.toString())
                putExtra("mumalange",numMumalange.toString())
                putExtra("total cost",(numDay*totalCost).toString())

                putExtra("mon",mon.toString())
                putExtra("tue",tue.toString())
                putExtra("wed",wed.toString())
                putExtra("thu",thu.toString())
                putExtra("fri",fri.toString())
                putExtra("sat",sat.toString())
                putExtra("sun",sun.toString())

            }
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}
