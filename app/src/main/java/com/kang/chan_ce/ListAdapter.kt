package com.kang.chan_ce

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListAdapter(private val context: Context): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var storeList = mutableListOf<StoreData>()

    fun setListData(data:MutableList<StoreData>){
        storeList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val store : StoreData = storeList[position]
        holder.storeName.text = store.storeName
        holder.storeLocation.text = store.storeLocation
        holder.storeTime.text = store.storeTime

    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val storeName: TextView = itemView.findViewById(R.id.storeName)
        val storeLocation: TextView = itemView.findViewById(R.id.storeLocation)
        val storeTime: TextView = itemView.findViewById(R.id.storeTime)




/*        fun bind(item: StoreData) {
            storeName.text = item.storeName
            storeLocation.text = item.storeLocation
            storeTime.text = item.storeTime
            //Glide.with(itemView).load(item.img).into(imgProfile)

            itemView.setOnClickListener {
                Intent(context, StoreDetailActivity::class.java).apply {
                    putExtra("name", storeLocation.toString())
                    putExtra("location", storeLocation.toString())
                    putExtra("time", storeTime.toString())
                    //addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }.run { context.startActivity(this) }
            }

        }*/
    }

}