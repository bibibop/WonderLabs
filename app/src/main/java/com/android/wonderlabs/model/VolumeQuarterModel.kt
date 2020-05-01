package com.android.wonderlabs.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.android.wonderlabs.utils.TABLE_NAME
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_NAME, indices = [Index(value = ["quarter"], unique = true)])
data class VolumeQuarterModel(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "volume_of_mobile_data") @SerializedName("volume_of_mobile_data") var volumeData: String = "",
    @ColumnInfo(name = "quarter") @SerializedName("quarter") var quarter: String = "",
    var isDecrease: Boolean = false
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VolumeQuarterModel?>() {
            override fun areItemsTheSame(oldItem: VolumeQuarterModel, newItem: VolumeQuarterModel) =
                oldItem.quarter == newItem.quarter

            override fun areContentsTheSame(oldItem: VolumeQuarterModel, newItem: VolumeQuarterModel) =
                oldItem.volumeData == newItem.volumeData
        }
    }
}