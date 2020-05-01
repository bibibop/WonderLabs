package com.android.wonderlabs.model

import com.google.gson.annotations.SerializedName

class DataResponse {
    @SerializedName("help")
    val help: String = ""

    @SerializedName("result")
    val result: ResultData = ResultData()

    @SerializedName("success")
    val success: Boolean = false
}