package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kang.chan_ce.databinding.ActivityStoreDetailBinding
import kotlinx.android.synthetic.main.item.view.*
import android.R
import android.view.View

import android.view.ViewGroup
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapPOIItem

class StoreDetailActivity: AppCompatActivity() {

//    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //map view
//        val mapView = MapView(this)
//        binding.mapView.addView(mapView)
//
//
//
//        val xlatitude = intent.getStringExtra("x")?.toDouble()
//        val ylatitude = intent.getStringExtra("y")?.toDouble()
//        Log.e("위치","$ylatitude")
//        val mapPoint = xlatitude?.let { ylatitude?.let { it1 ->
//            MapPoint.mapPointWithGeoCoord(it,
//                it1
//            )
//        } }
//
//        mapView.setMapCenterPoint(mapPoint, true)
//        mapView.setZoomLevel(1, true)
//
//        //marker 설정
//        //marker 설정
//        val marker = MapPOIItem()
//        marker.itemName = "Default Marker"
//        //marker.setTag(0);
//        //marker.setTag(0);
//        marker.mapPoint = mapPoint
//        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
//        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//        mapView.addPOIItem(marker)


        val url = intent.getStringExtra("StoreImage")
        val store = intent.getStringExtra("StoreName")


        Glide.with(binding.storeImage).load(url).into(binding.storeImage)
        binding.storeName.setText(intent.getStringExtra("StoreName"))
        binding.storeLocation.setText(intent.getStringExtra("StoreLocation"))
        binding.storeTime.setText(intent.getStringExtra("StoreTime"))
        binding.storeIntro.setText(intent.getStringExtra("StoreIntro"))
        binding.storeNum.text = intent.getStringExtra("StoreNum")

        binding.menuName.setText(intent.getStringExtra("StoreMenu"))
        Glide.with(binding.menuImage).load(intent.getStringExtra("menuImage")).into(binding.menuImage)
        binding.menuIntro.setText(intent.getStringExtra("menuIntro"))
        binding.menuPrice.setText(intent.getStringExtra("menuPrice"))

        binding.menuName1.text = intent.getStringExtra("StoreMenu1")
        Glide.with(binding.menuImage1).load(intent.getStringExtra("menuImage1")).into(binding.menuImage1)
        binding.menuIntro1.setText(intent.getStringExtra("menuIntro1"))
        binding.menuPrice1.setText(intent.getStringExtra("menuPrice1"))

        binding.menuName2.setText(intent.getStringExtra("StoreMenu2"))
        Glide.with(binding.menuImage2).load(intent.getStringExtra("menuImage2")).into(binding.menuImage2)
        binding.menuIntro2.setText(intent.getStringExtra("menuIntro2"))
        binding.menuPrice2.setText(intent.getStringExtra("menuPrice2"))


        binding.btnSelect.setOnClickListener {
//            Log.e("안녕 여기 로그좀","클릭")

            val intent = Intent(this, SubscriptionActivity::class.java).apply {
                putExtra("StoreName",store)
                putExtra("StoreMenu",intent.getStringExtra("StoreMenu"))
                putExtra("StoreMenu1",intent.getStringExtra("StoreMenu1"))
                putExtra("StoreMenu2",intent.getStringExtra("StoreMenu2"))
            }

            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}