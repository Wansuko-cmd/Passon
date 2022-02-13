package com.wsr.index

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.wsr.R
import com.wsr.databinding.FragmentIndexBinding
import com.wsr.state.consume
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
        setHasOptionsMenu(true)
        _binding = FragmentIndexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.index_menu, menu)
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

                indexUiState.passwordGroupsState.consume(
                    onSuccess = { indexEpoxyController.setData(it) },
                    onFailure = {
                        Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_LONG,
                        ).show()
                    },
                    onLoading = {},
                )
            }
        }
    }

    private fun navigateToShow(id: String) {
        val action = IndexFragmentDirections.actionIndexFragmentToShowFragment(id)
        findNavController().navigate(action)
    }
}

