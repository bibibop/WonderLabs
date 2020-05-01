package com.android.wonderlabs.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.wonderlabs.model.VolumeQuarterModel
import com.android.wonderlabs.utils.TABLE_NAME

@Dao
interface VolumeDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllVolumes(): List<VolumeQuarterModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertVolume(record: VolumeQuarterModel)
}