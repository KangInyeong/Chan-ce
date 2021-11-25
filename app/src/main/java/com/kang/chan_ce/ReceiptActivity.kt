package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kang.chan_ce.databinding.ActivityReceiptBinding
import com.kang.chan_ce.databinding.ActivityStoreDetailBinding

class ReceiptActivity :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        binding.btnDone.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java).apply {
                putExtra("total cost",totalCost.toString())
            }
            startActivity(intent)
        }


        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}