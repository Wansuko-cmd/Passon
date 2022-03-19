package com.wsr.index

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.wsr.R
import com.wsr.databinding.FragmentIndexBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogFragment
import com.wsr.state.consume
import org.koin.androidx.viewmodel.ext.android.viewModel

class IndexFragment : Fragment(R.layout.fragment_index) {

    private lateinit var indexEpoxyController: IndexEpoxyController
    private lateinit var indexRecyclerView: RecyclerView
    private val indexViewModel: IndexViewModel by viewModel()

    private val email by lazy { "example1@gmail.com" }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.index_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentIndexBinding.bind(view)

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            show()
            setDisplayHomeAsUpEnabled(false)
        }

        indexViewModel.fetch(email)

        indexEpoxyController = IndexEpoxyController(
            onClick = ::navigateToShow,
            resources = resources,
        )

        indexRecyclerView = binding.indexFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = indexEpoxyController.adapter
        }

        binding.indexFragmentFab.setOnClickListener {
            showDialogIfNotDrew(tag) {
                IndexCreatePasswordGroupDialogFragment.create(indexViewModel, email)
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            indexViewModel.uiState.collect { indexUiState ->
                indexUiState.passwordGroupsState.consume(
                    success = indexEpoxyController::setData,
                    failure = ::showErrorMessage,
                    loading = { /* do nothing */ },
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            indexViewModel.indexRefreshEvent.collect { indexViewModel.fetch(email) }
        }
        launchInLifecycleScope(Lifecycle.State.STARTED) {
            indexViewModel.navigateToEditEvent.collect { navigateToEdit(it) }
        }
    }

    private fun navigateToShow(passwordGroupId: String) {
        val action = IndexFragmentDirections.actionIndexFragmentToShowFragment(passwordGroupId)
        findNavController().navigate(action)
    }

    private fun navigateToEdit(passwordGroupId: String) {
        val action = IndexFragmentDirections.actionIndexFragmentToEditFragment(passwordGroupId)
        findNavController().navigate(action)
    }

    private fun showErrorMessage(errorIndexUiState: ErrorIndexUiState) =
        Toast.makeText(
            context,
            errorIndexUiState.message,
            Toast.LENGTH_LONG,
        ).show()

    private fun showDialogIfNotDrew(tag: String?, builder: () -> DialogFragment) {
        if (isNotDrewDialogWithThisTag(tag)) builder().showNow(
            requireActivity().supportFragmentManager,
            tag
        )
    }

    private fun isNotDrewDialogWithThisTag(tag: String?) =
        (requireActivity().supportFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dialog == null
}
