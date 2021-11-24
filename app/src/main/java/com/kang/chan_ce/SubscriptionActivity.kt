package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kang.chan_ce.databinding.ActivitySubscriptionBinding
import android.widget.TextView




class SubscriptionActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        var num = 0

        // plus & minus
        var count1 = 0
        var count2 = 0
        var count3 = 0
        var totalCost = 0

        //temp banchan price
        var kimchi = 1000
        var jinmichae = 2000
        var mumalange = 1500

        //select day
        binding.btnMON.setOnClickListener {
            binding.btnMON?.isSelected = binding.btnMON.isSelected != true
            if (binding.btnMON.isSelected == true){
                num++
                binding.btnTotal.setText((num*totalCost).toString())
            }
            else{
                num--
                binding.btnTotal.setText((num*totalCost).toString())
            }
        }

        binding.btnTUE.setOnClickListener {
            binding.btnTUE?.isSelected = binding.btnTUE.isSelected != true
            if (binding.btnTUE.isSelected == true){
                num++
                binding.btnTotal.setText((num*totalCost).toString())
            }
            else{
                num--
                binding.btnTotal.setText((num*totalCost).toString())
            }
        }

        binding.btnWED.setOnClickListener {
            binding.btnWED?.isSelected = binding.btnWED.isSelected != true
            if (binding.btnWED.isSelected == true){
                num++
                binding.btnTotal.setText((num*totalCost).toString())
            }
            else{
                num--
                binding.btnTotal.setText((num*totalCost).toString())
            }
        }

        binding.btnTHU.setOnClickListener {
            binding.btnTHU?.isSelected = binding.btnTHU.isSelected != true
            if (binding.btnTHU.isSelected == true){
                num++
                binding.btnTotal.setText((num*totalCost).toString())
            }
            else{
                num--
                binding.btnTotal.setText((num*totalCost).toString())
            }

        }

        binding.btnFRI.setOnClickListener {
            binding.btnFRI?.isSelected = binding.btnFRI.isSelected != true
            if (binding.btnFRI.isSelected == true){
                num++
                binding.btnTotal.setText((num*totalCost).toString())
            }
            else{
                num--
                binding.btnTotal.setText((num*totalCost).toString())
            }
        }

        binding.btnSAT.setOnClickListener {
            binding.btnSAT?.isSelected = binding.btnSAT.isSelected != true
            if (binding.btnSAT.isSelected == true){
                num++
                binding.btnTotal.setText((num*totalCost).toString())
            }
            else{
                num--
                binding.btnTotal.setText((num*totalCost).toString())
            }
        }

        binding.btnSUN.setOnClickListener {
            binding.btnSUN?.isSelected = binding.btnSUN.isSelected != true
            if (binding.btnSUN.isSelected == true){
                num++
                binding.btnTotal.setText((num*totalCost).toString())
            }
            else{
                num--
                binding.btnTotal.setText((num*totalCost).toString())
            }
        }

        binding.btnDone.setOnClickListener {
            val intent = Intent(this, ReceiptActivity::class.java)
            startActivity(intent)
        }


        binding.btnAdd1.setOnClickListener {
            count1++
            totalCost+=kimchi
            binding.btnCount1.setText(count1.toString())
            binding.btnTotal.setText((num*totalCost).toString())
        }

        binding.btnAdd2.setOnClickListener {
            count2++
            totalCost+=jinmichae
            binding.btnCount2.setText(count2.toString())
            binding.btnTotal.setText((num*totalCost).toString())
        }

        binding.btnAdd3.setOnClickListener {
            count3++
            totalCost+=mumalange
            binding.btnCount3.setText(count3.toString())
            binding.btnTotal.setText((num*totalCost).toString())
        }

        binding.btnMinus1.setOnClickListener {
            if (count1 > 0) {
                count1--
                totalCost-=kimchi
                binding.btnCount1.setText(count1.toString())
                binding.btnTotal.setText((num*totalCost).toString())
            }else {}
        }

        binding.btnMinus2.setOnClickListener {
            if (count2 > 0) {
                count2--
                totalCost-=jinmichae
                binding.btnCount2.setText(count2.toString())
                binding.btnTotal.setText((num*totalCost).toString())
            }else {}
        }

        binding.btnMinus3.setOnClickListener {
            if (count3 > 0) {
                count3--
                totalCost-=mumalange
                binding.btnCount3.setText(count3.toString())
                binding.btnTotal.setText((num*totalCost).toString())
            }else {}
        }







        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}
