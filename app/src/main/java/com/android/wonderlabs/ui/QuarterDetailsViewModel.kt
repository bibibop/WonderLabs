package com.android.wonderlabs.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.wonderlabs.data.AppDatabase
import com.android.wonderlabs.data.VolumeDao
import com.android.wonderlabs.model.VolumeQuarterModel

class QuarterDetailsViewModel : ViewModel() {
    val data = MutableLiveData<List<VolumeQuarterModel>>()
    private var volumeProvider: VolumeDao? = null

    fun loadDataFromDB(context: Context, year: String) {
        volumeProvider = AppDatabase.create(context).volumeDao()

        if (volumeProvider != null) {
            val records = volumeProvider!!.getAllVolumes()
            val results = records.filter { it.quarter.startsWith(year) }
            data.postValue(results)
        }
    }
}
