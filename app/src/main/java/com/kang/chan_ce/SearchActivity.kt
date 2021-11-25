package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item.view.*

class SearchActivity:AppCompatActivity() {

    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        firestore = FirebaseFirestore.getInstance()

        recyclerview.adapter = RecyclerViewAdapter()
        recyclerview.layoutManager = LinearLayoutManager(this)
        
        btnBack.setOnClickListener {
            finish()
        }

    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var storeList : ArrayList<StoreData> = arrayListOf()

        init {
            firestore?.collection("StoreList")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                storeList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(StoreData::class.java)
                    storeList.add(item!!)
                }

                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            init {
                itemView.itemlayout.setOnClickListener {
                    Log.e("호잉요","${storeList[position].storeMenu?.get(1)?.get("menuPrice")}")
                    Log.e("호아","${storeList[position].storeMenu?.get(2)?.get("menuIntro")}")
                    val intent = Intent(view.context, StoreDetailActivity::class.java).apply {
                        putExtra("StoreImage",storeList[position].storeImage)
                        putExtra("StoreName",storeList[position].storeName)
                        putExtra("StoreLocation",storeList[position].storeLocation)
                        putExtra("StoreTime",storeList[position].storeTime)
                        putExtra("StoreIntro",storeList[position].storeIntro)
                        putExtra("StoreNum",storeList[position].storeNum)
                        putExtra("StoreMenu",
                            storeList[position].storeMenu?.get(0)?.get("menuName")
                        )
                        putExtra("menuImage",
                            storeList[position].storeMenu?.get(0)?.get("menuImage")
                        )
                        putExtra("menuIntro",
                            storeList[position].storeMenu?.get(0)?.get("menuIntro")
                        )
                        putExtra("menuPrice",
                            storeList[position].storeMenu?.get(0)?.get("menuPrice")
                        )

                        putExtra("StoreMenu1",
                            storeList[position].storeMenu?.get(1)?.get("menuName")
                        )
                        putExtra("menuImage1",
                            storeList[position].storeMenu?.get(1)?.get("menuImage")
                        )
                        putExtra("menuIntro1",
                            storeList[position].storeMenu?.get(1)?.get("menuIntro")
                        )
                        putExtra("menuPrice1",
                            storeList[position].storeMenu?.get(1)?.get("menuPrice")
                        )

                        putExtra("StoreMenu2",
                            storeList[position].storeMenu?.get(2)?.get("menuName")
                        )
                        putExtra("menuImage2",
                            storeList[position].storeMenu?.get(2)?.get("menuImage")
                        )
                        putExtra("menuIntro2",
                            storeList[position].storeMenu?.get(2)?.get("menuIntro")
                        )
                        putExtra("menuPrice2",
                            storeList[position].storeMenu?.get(2)?.get("menuPrice")
                        )
                        putExtra("x",storeList[position].xlatitude)
                        putExtra("y",storeList[position].ylatitude)
                        Log.e("위치다","${storeList[position].ylatitude}")

                    }
                    startActivity(intent)
                }
//                view.setOnClickListener {
//                    Toast.makeText(view.context,"와아g", Toast.LENGTH_SHORT).show()
//                }
            }

        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView

            Glide.with(viewHolder.storeImage).load(storeList[position].storeImage).into(viewHolder.storeImage)
            //Log.e("안녕 여기 로그좀","$storeImage ${storeList[position].storeImage}")

            viewHolder.storeName.text = storeList[position].storeName
            viewHolder.storeLocation.text = storeList[position].storeLocation
            viewHolder.storeTime.text = storeList[position].storeTime

        }

        override fun getItemCount(): Int {
            return storeList.size
        }
    }

}