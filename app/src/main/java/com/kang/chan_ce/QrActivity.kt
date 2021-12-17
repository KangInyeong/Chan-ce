package com.kang.chan_ce

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.kang.chan_ce.databinding.ActivityMypageBinding
import com.kang.chan_ce.databinding.ActivityQrBinding
import com.kang.chan_ce.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_qr.*

class QrActivity : AppCompatActivity() {

    lateinit var binding: ActivityQrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username = intent.getStringExtra("userName").toString()

        qrtext.setText("This is ${username}'s QR code.")

        //qr 생성 : https://github.com/journeyapps/zxing-android-embedded
        var url = "https://github.com/KangInyeong/Chan-ce"
        val barcodeEncoder = BarcodeEncoder()
        val bitmap: Bitmap = barcodeEncoder.encodeBitmap(url, BarcodeFormat.QR_CODE, 400, 400)
        binding.qr.setImageBitmap(bitmap)

/*        binding.btnCreate.setOnClickListener {

            //qr 생성 : https://github.com/journeyapps/zxing-android-embedded
            var url = "https://github.com/KangInyeong/Chan-ce"
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(url, BarcodeFormat.QR_CODE, 400, 400)
            binding.qr.setImageBitmap(bitmap)
        }*/

        binding.btnMyPage.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

    }
}
