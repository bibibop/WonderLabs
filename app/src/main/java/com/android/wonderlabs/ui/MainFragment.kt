package com.android.wonderlabs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.wonderlabs.R
import com.android.wonderlabs.ui.adapter.MainAdapter
import com.android.wonderlabs.utils.YEAR
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.Serializable

class MainFragment : Fragment() {
    companion object {
        fun newInstance(getViewModel: () -> MainViewModel) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("getViewModel", getViewModel as Serializable)
                }
            }
    }

    private lateinit var viewModel: MainViewModel

    private val adapter by lazy {
        MainAdapter({
            val bundle = bundleOf(YEAR to it.year)
            val quarterFragment = QuarterDetailsFragment()
            quarterFragment.arguments = bundle
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            quarterFragment.show(transaction, it.year)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        observeChanges()
    }

    private fun initUI() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        recyclerView.adapter = adapter
        adapter.submitList(viewModel.data.value)
        viewModel.getData(requireContext())
        swipeRefresh.setOnRefreshListener {
            viewModel.getData(requireContext())
            swipeRefresh.isRefreshing = false
        }
    }

    private fun observeChanges() {
        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            if (data.size == 0) {
                Toast.makeText(
                    requireContext(),
                    "Please check your internet connection and try again!",
                    LENGTH_LONG
                ).show()
            } else {
                adapter.submitList(viewModel.data.value)
                adapter.notifyDataSetChanged()
            }
        })
    }
}
