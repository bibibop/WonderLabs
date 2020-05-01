package com.android.wonderlabs.model

import com.google.gson.annotations.SerializedName

class ResultData {
    @SerializedName("fields")
    val fields: List<FieldData> = emptyList()

    @SerializedName("records")
    val records: List<VolumeQuarterModel> = emptyList()

    @SerializedName("resource_id")
    val resourceID: String = ""

    @SerializedName("total")
    val total: Int = 0
}