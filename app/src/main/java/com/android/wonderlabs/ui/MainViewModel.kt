package com.android.wonderlabs.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.wonderlabs.data.AppDatabase
import com.android.wonderlabs.data.VolumeDao
import com.android.wonderlabs.model.VolumeQuarterModel
import com.android.wonderlabs.model.VolumeYearModel
import com.android.wonderlabs.net.APIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MainViewModel : ViewModel() {
    private var apiService: APIService? = null
    private var volumeProvider: VolumeDao? = null

    val data = MutableLiveData<List<VolumeYearModel>>()
    val decreasePosition = MutableLiveData<List<Int>>()

    @SuppressLint("CheckResult")
    fun getData(context: Context) {
        apiService = APIService.create()
        volumeProvider = AppDatabase.create(context).volumeDao()

        if (apiService != null) {
            apiService!!.getData("a807b7ab-6cad-4aa6-87d0-e283a7353a0f")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> updateDataDB(result.result.records) },
                    { error -> loadDataFromDB() }
                )
        }
    }

    private fun updateDataDB(records: List<VolumeQuarterModel>) {
        if (volumeProvider != null) {
            records.forEach { record ->
                volumeProvider!!.insertVolume(record)
            }
        }

        filterResult(records)
    }

    private fun loadDataFromDB() {
        if (volumeProvider != null) {
            val records = volumeProvider!!.getAllVolumes()
            filterResult(records)
        }
    }

    private fun filterResult(records: List<VolumeQuarterModel>) {
        val results = mutableListOf<VolumeYearModel>()
        val decreasePositions = mutableListOf<Int>()
        val years = records.map { it.quarter.substring(0, 4) }.distinctBy { it }

        years.forEach {
            var totalVolume = 0.0
            var isDecrease = false
            var tmpVolume = 0.0

            val groups =
                records.groupBy { record -> record.quarter.startsWith(it) }

            groups.getValue(true).forEach {
                try {
                    if (it.volumeData.toDouble() < tmpVolume) {
                        isDecrease = true
                    }

                    totalVolume += it.volumeData.toDouble()
                    tmpVolume = it.volumeData.toDouble()
                } catch (e: Exception) {
                }
            }

            if (isDecrease) {
                decreasePositions.add(results.size)
            }
            results.add(VolumeYearModel(it, totalVolume.toBigDecimal().toPlainString(), isDecrease))
        }

        data.postValue(results)
        decreasePosition.postValue(decreasePositions)
    }
}
