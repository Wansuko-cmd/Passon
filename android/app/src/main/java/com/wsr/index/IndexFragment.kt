package com.wsr.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.wsr.databinding.FragmentIndexBinding
import com.wsr.state.State
import com.wsr.utils.launchInLifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class IndexFragment : Fragment() {

    private lateinit var _binding: FragmentIndexBinding
    private val binding get() = _binding

    private lateinit var indexEpoxyController: IndexEpoxyController
    private lateinit var indexRecyclerView: RecyclerView
    private val indexViewModel: IndexViewModel by viewModel()

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

        indexViewModel.fetchPasswordGroups("example1@gmail.com")

        indexEpoxyController = IndexEpoxyController(::navigateToShow)

        indexRecyclerView = binding.indexFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = indexEpoxyController.adapter
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            indexViewModel.uiState.collect { indexUiState ->
                when (indexUiState.passwordGroupsState) {
                    is State.Loading -> {}
                    is State.Success -> {
                        indexEpoxyController.setData(indexUiState.passwordGroupsState.value)
                    }
                    is State.Failure -> {
                        Toast.makeText(
                            context,
                            indexUiState.passwordGroupsState.value.message,
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
            }
        }
    }

    private fun navigateToShow(id: String) {
        val action = IndexFragmentDirections.actionIndexFragmentToShowFragment(id)
        findNavController().navigate(action)
    }
}

