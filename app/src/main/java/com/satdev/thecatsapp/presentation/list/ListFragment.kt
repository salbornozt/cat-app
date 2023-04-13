package com.satdev.thecatsapp.presentation.list

import android.app.ProgressDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.satdev.thecatsapp.R
import com.satdev.thecatsapp.data.util.Resource
import com.satdev.thecatsapp.databinding.FragmentListBinding
import com.satdev.thecatsapp.presentation.adapter.BreedListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), BreedListAdapter.BreedFilterListener {



    private val viewModel: ListViewModel by viewModels()
    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: BreedListAdapter? = null
    private val adapter get() = _adapter!!

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initProgressDialog()
        viewModel.catsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    showProgressDialog(true)
                }
                is Resource.Success -> {
                    showProgressDialog(false)
                    adapter.setBreedList(it.data ?: listOf())
                    initSearchFilter()

                }
                is Resource.Error -> {
                    showProgressDialog(false)

                }
            }
        })
    }

    private fun initSearchFilter() {
        binding.breedListSearch.addTextChangedListener {
            adapter.filter.filter(it.toString())
        }
    }

    private fun showProgressDialog(show: Boolean) {
        if (show) {
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }

    private fun initProgressDialog() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setTitle(R.string.progress_dialog_title)
        progressDialog.setMessage(getString(R.string.progress_dialog_message))
    }

    private fun initRecyclerView() {
        _adapter = BreedListAdapter(this)
        binding.breedList.layoutManager = LinearLayoutManager(activity)
        binding.breedList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }

    override fun onFilterResult(count: Int) {

    }
}