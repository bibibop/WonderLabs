package com.android.wonderlabs.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflater(@LayoutRes id: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(id, this, attachToRoot)
}