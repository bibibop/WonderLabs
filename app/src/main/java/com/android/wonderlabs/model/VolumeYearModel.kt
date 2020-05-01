package com.android.wonderlabs.model

import androidx.recyclerview.widget.DiffUtil

class VolumeYearModel(
    val year: String = "",
    val totalVolume: String = "",
    val isDecrease: Boolean = false
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VolumeYearModel?>() {
            override fun areItemsTheSame(oldItem: VolumeYearModel, newItem: VolumeYearModel) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: VolumeYearModel, newItem: VolumeYearModel) =
                oldItem.totalVolume == newItem.totalVolume
        }
    }
}