package com.kang.chan_ce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {
    private val repo = Repo()
    fun fetchData(): LiveData<MutableList<StoreData>> {
        val mutableData = MutableLiveData<MutableList<StoreData>>()
        repo.getData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}