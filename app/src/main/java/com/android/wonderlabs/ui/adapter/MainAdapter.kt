package com.android.wonderlabs.ui.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import com.android.wonderlabs.R
import com.android.wonderlabs.extension.inflater
import com.android.wonderlabs.model.VolumeYearModel
import kotlinx.android.synthetic.main.row_volume.view.*

class MainAdapter(private val onClick: (VolumeYearModel) -> Unit) :
    BaseAdapter<VolumeYearModel>(VolumeYearModel.DIFF_CALLBACK) {

    override fun contentViewHolder(parent: ViewGroup, viewType: Int) =
        MainViewHolder(parent, onClick)

    class MainViewHolder(parent: ViewGroup, private val onClick: (VolumeYearModel) -> Unit) :
        BaseViewHolder<VolumeYearModel>(parent.inflater(R.layout.row_volume)) {
        override fun bind(item: VolumeYearModel) {
            itemView.apply {
                tvTitle.text = item.year
                tvContent.text = item.totalVolume
                if (item.isDecrease) {
                    image.isVisible = true
                    image.setBackgroundResource(R.drawable.ic_decrease_volume)
                } else {
                    image.isVisible = false
                }

                itemView.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}