package com.wsr.view.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wsr.databinding.FragmentIndexBinding
import com.wsr.view_model.index.IndexViewModel

class IndexFragment : Fragment() {

    private lateinit var _binding: FragmentIndexBinding
    private val binding get() = _binding

    lateinit var indexRecyclerView: RecyclerView
    lateinit var indexAdapter: IndexAdapter
    lateinit var indexViewModel: IndexViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        indexViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(IndexViewModel::class.java)

        indexAdapter = IndexAdapter()
        indexRecyclerView = binding.indexFragmentRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = indexAdapter
        }

        indexAdapter.submitList(indexViewModel.getAllPasswordGroup("example1@gmail.com"))
    }
}