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

class StoreDetailActivity: AppCompatActivity() {

//    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        firestore = FirebaseFirestore.getInstance()




//        var menuList : ArrayList<MenuData> = arrayListOf()
//
//        db.collection("menuList").get().addOnSuccessListener { result ->
//            for (document in result){
//                val menuList = document[menu]
//            }
//        }

//        firestore?.collection("MenuList")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//
//            menuList.clear()
//
//            for(snapshot in querySnapshot!!.documents){
//                var menu = snapshot.toObject(MenuData::class.java)
//                menuList.add(menu!!)
//            }
//        }
        //notifyDataSetChanged()


//        storedata = intent.getSerializableExtra("data") as StoreData

//        Glide.with(this).load(storedata.storeImage).into(storeImage)
//        storeName.text = storedata.storeName

        val url = intent.getStringExtra("StoreImage")

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

        binding.menuName1.setText(intent.getStringExtra("StoreMenu1"))
        Glide.with(binding.menuImage1).load(intent.getStringExtra("menuImage1")).into(binding.menuImage1)
        binding.menuIntro1.setText(intent.getStringExtra("MenuIntro1"))
        binding.menuPrice1.setText(intent.getStringExtra("MenuPrice1"))

        binding.menuName2.setText(intent.getStringExtra("StoreMenu2"))
        Glide.with(binding.menuImage2).load(intent.getStringExtra("menuImage2")).into(binding.menuImage2)
        binding.menuIntro2.setText(intent.getStringExtra("MenuIntro2"))
        binding.menuPrice2.setText(intent.getStringExtra("MenuPrice2"))


        binding.btnSelect.setOnClickListener {
//            Log.e("안녕 여기 로그좀","클릭")


            val intent = Intent(this, SubscriptionActivity::class.java).apply {
                putExtra("StoreName",intent.getStringExtra("StoreName"))
            }

            startActivity(intent)
        }

        binding.btnBack.setOnClickListener{
            finish()
        }
    }
}