package com.wsr.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.wsr.databinding.FragmentIndexBinding
import com.wsr.utils.execute
import kotlinx.coroutines.launch

class IndexFragment : Fragment() {

    private lateinit var _binding: FragmentIndexBinding
    private val binding get() = _binding

    private lateinit var indexEpoxyController: IndexEpoxyController
    private lateinit var indexRecyclerView: RecyclerView
    private lateinit var indexViewModel: IndexViewModel

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
        ).get(IndexViewModel::class.java).apply {
            fetchPasswordGroup("example1@gmail.com")
        }

        indexEpoxyController = IndexEpoxyController()

        indexRecyclerView = binding.indexFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = indexEpoxyController.adapter
        }

        lifecycleScope.launch {
            indexViewModel.uiState.collect {
                it.passwordGroupsState.execute(
                    onLoading = {},
                    onSuccess = { list -> indexEpoxyController.setData(list) },
                    onFailure = { state ->
                        Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                    },
                )
            }
        }
    }
}