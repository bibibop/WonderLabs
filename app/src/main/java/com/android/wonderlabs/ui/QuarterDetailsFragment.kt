package com.android.wonderlabs.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.wonderlabs.R
import com.android.wonderlabs.ui.adapter.QuarterDetailsAdapter
import com.android.wonderlabs.utils.YEAR
import kotlinx.android.synthetic.main.quarter_details_fragment.*

class QuarterDetailsFragment : DialogFragment() {
    private lateinit var viewModel: QuarterDetailsViewModel

    private val year by lazy { arguments?.getString(YEAR) ?: "" }

    private val adapter by lazy {
        QuarterDetailsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.quarter_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuarterDetailsViewModel::class.java)

        viewModel.loadDataFromDB(requireContext(), year)
        observeChanges()
    }


    private fun observeChanges() {
        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            recyclerViewDetails.adapter = adapter
            adapter.submitList(data)
            adapter.notifyDataSetChanged()
        })
    }
}
