package com.kang.chan_ce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.kang.chan_ce.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
        options.setPrompt("Scan a barcode") // 텍스트
        options.setCameraId(0)  // Use a specific camera of the device
        options.setBeepEnabled(false) //스캔소리
        options.setBarcodeImageEnabled(true)
        IntentIntegrator(this).initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result: IntentResult = IntentIntegrator.parseActivityResult(resultCode,resultCode,data)

        if(result != null){
            if(result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
            }
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MypageActivity::class.java))
        finish()
    }
}