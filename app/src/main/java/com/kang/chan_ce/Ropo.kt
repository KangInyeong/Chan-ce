package com.kang.chan_ce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Repo {
    fun getData(): LiveData<MutableList<StoreData>> {
        val mutableData = MutableLiveData<MutableList<StoreData>>()
        val database = Firebase.database
        val myRef = database.getReference("StoreData")
        myRef.addValueEventListener(object : ValueEventListener {
            val listData: MutableList<StoreData> = mutableListOf<StoreData>()
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (storeSnapshot in snapshot.children){
                        val getData = storeSnapshot.getValue(StoreData::class.java)
                        listData.add(getData!!)

                        mutableData.value = listData
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return mutableData
    }
}