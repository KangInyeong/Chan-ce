package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item.*
import kotlinx.android.synthetic.main.item.view.*

class SearchActivity:AppCompatActivity() {

    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        firestore = FirebaseFirestore.getInstance()

        recyclerview.adapter = RecyclerViewAdapter()
        recyclerview.layoutManager = LinearLayoutManager(this)
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
                    val intent = Intent(view.context, StoreDetailActivity::class.java).apply {
                        putExtra("StoreImage",storeList[position].storeImage)
                        putExtra("StoreName",storeList[position].storeName)
                        putExtra("StoreLocation",storeList[position].storeLocation)
                        putExtra("StoreTime",storeList[position].storeTime)
                        putExtra("StoreIntro",storeList[position].storeIntro)
                        //putExtra("StoreMenu",storeList[position].storeMenu)
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