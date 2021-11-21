package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kang.chan_ce.databinding.ActivityStoreDetailBinding
import com.kang.chan_ce.databinding.ActivitySubscriptionBinding

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

        //select day
        binding.btnMON.setOnClickListener {
            binding.btnMON?.isSelected = binding.btnMON.isSelected != true
        }

        binding.btnTUE.setOnClickListener {
            binding.btnTUE?.isSelected = binding.btnTUE.isSelected != true
        }

        binding.btnWED.setOnClickListener {
            binding.btnWED?.isSelected = binding.btnWED.isSelected != true
        }

        binding.btnTHU.setOnClickListener {
            binding.btnTHU?.isSelected = binding.btnTHU.isSelected != true
        }

        binding.btnFRI.setOnClickListener {
            binding.btnFRI?.isSelected = binding.btnFRI.isSelected != true
        }

        binding.btnSAT.setOnClickListener {
            binding.btnSAT?.isSelected = binding.btnSAT.isSelected != true
        }

        binding.btnSUN.setOnClickListener {
            binding.btnSUN?.isSelected = binding.btnSUN.isSelected != true
        }

        binding.btnDone.setOnClickListener {
            val intent = Intent(this, ReceiptActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}
