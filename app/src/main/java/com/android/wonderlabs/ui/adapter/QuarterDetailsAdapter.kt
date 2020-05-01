package com.android.wonderlabs.ui.adapter

import android.view.ViewGroup
import com.android.wonderlabs.R
import com.android.wonderlabs.extension.inflater
import com.android.wonderlabs.model.VolumeQuarterModel
import kotlinx.android.synthetic.main.row_volume.view.*

class QuarterDetailsAdapter() :
    BaseAdapter<VolumeQuarterModel>(VolumeQuarterModel.DIFF_CALLBACK) {

    override fun contentViewHolder(parent: ViewGroup, viewType: Int) =
        QuarterDetailsViewHolder(parent)

    class QuarterDetailsViewHolder(parent: ViewGroup) :
        BaseViewHolder<VolumeQuarterModel>(parent.inflater(R.layout.row_volume)) {
        override fun bind(item: VolumeQuarterModel) {
            itemView.apply {
                tvTitle.text = item.quarter
                tvContent.text = item.volumeData
            }
        }
    }
}