package com.kang.chan_ce

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.item.view.*

class SearchActivity:AppCompatActivity() {

    var firestore : FirebaseFirestore? = null
    var adapter: RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        firestore = FirebaseFirestore.getInstance()

        adapter = RecyclerViewAdapter()

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        searchview.setOnQueryTextListener( object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //Toast.makeText(this, "입력하고있는 단어 = $query", Toast.LENGTH_LONG).show()
                adapter!!.filter.filter(query)
                return false
            }

        })
        
        btnBack.setOnClickListener {
            finish()
        }

    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

        var storeFilteredList : ArrayList<StoreData> = arrayListOf()
        var storeList : ArrayList<StoreData> = arrayListOf()

        init {

            firestore?.collection("StoreList")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                storeList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(StoreData::class.java)
                    storeList.add(item!!)
                }

                storeFilteredList = storeList

                notifyDataSetChanged()
            }
        }

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    var list = storeList as ArrayList<StoreData>
                    val charSearch = constraint.toString()
                    if (charSearch.isEmpty()) {
                        storeFilteredList = list
                    } else {
                        val resultList = ArrayList<StoreData>()
                        for (row in list) {
                            if (row != null) {
                                if (row.storeName?.lowercase()?.contains(constraint.toString().lowercase()) == true) {
                                    resultList.add(row)
                                }
                            }
                        }
                        storeFilteredList = resultList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = storeFilteredList
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    storeFilteredList = results?.values as ArrayList<StoreData>
                    notifyDataSetChanged()
                }
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
                        putExtra("x",storeFilteredList[position].ylatitude)
                        putExtra("y",storeFilteredList[position].xlatitude)
                        putExtra("StoreImage",storeFilteredList[position].storeImage)
                        putExtra("StoreName",storeFilteredList[position].storeName)
                        putExtra("StoreLocation",storeFilteredList[position].storeLocation)
                        putExtra("StoreTime",storeFilteredList[position].storeTime)
                        putExtra("StoreIntro",storeFilteredList[position].storeIntro)
                        putExtra("StoreNum",storeFilteredList[position].storeNum)
                        putExtra("StoreMenu",
                            storeFilteredList[position].storeMenu?.get(0)?.get("menuName")
                        )
                        putExtra("menuImage",
                            storeFilteredList[position].storeMenu?.get(0)?.get("menuImage")
                        )
                        putExtra("menuIntro",
                            storeFilteredList[position].storeMenu?.get(0)?.get("menuIntro")
                        )
                        putExtra("menuPrice",
                            storeFilteredList[position].storeMenu?.get(0)?.get("menuPrice")
                        )

                        putExtra("StoreMenu1",
                            storeFilteredList[position].storeMenu?.get(1)?.get("menuName")
                        )
                        putExtra("menuImage1",
                            storeFilteredList[position].storeMenu?.get(1)?.get("menuImage")
                        )
                        putExtra("menuIntro1",
                            storeFilteredList[position].storeMenu?.get(1)?.get("menuIntro")
                        )
                        putExtra("menuPrice1",
                            storeFilteredList[position].storeMenu?.get(1)?.get("menuPrice")
                        )

                        putExtra("StoreMenu2",
                            storeFilteredList[position].storeMenu?.get(2)?.get("menuName")
                        )
                        putExtra("menuImage2",
                            storeFilteredList[position].storeMenu?.get(2)?.get("menuImage")
                        )
                        putExtra("menuIntro2",
                            storeFilteredList[position].storeMenu?.get(2)?.get("menuIntro")
                        )
                        putExtra("menuPrice2",
                            storeFilteredList[position].storeMenu?.get(2)?.get("menuPrice")
                        )
                        putExtra("x",storeFilteredList[position].xlatitude)
                        putExtra("y",storeFilteredList[position].ylatitude)
                        Log.e("위치다","${storeFilteredList[position].ylatitude}")

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

            Glide.with(viewHolder.storeImage).load(storeFilteredList[position].storeImage).into(viewHolder.storeImage)
            //Log.e("안녕 여기 로그좀","$storeImage ${storeList[position].storeImage}")

            viewHolder.storeName.text = storeFilteredList[position].storeName
            viewHolder.storeLocation.text = storeFilteredList[position].storeLocation
            viewHolder.storeTime.text = storeFilteredList[position].storeTime

        }

        override fun getItemCount(): Int {
            return storeFilteredList.size
        }
    }

}