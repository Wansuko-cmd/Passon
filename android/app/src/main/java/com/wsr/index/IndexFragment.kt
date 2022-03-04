package com.wsr.index

import android.os.Bundle
import android.view.*
import android.widget.Toast
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

class IndexFragment : Fragment() {

    private lateinit var _binding: FragmentIndexBinding
    private val binding get() = _binding

    private lateinit var indexEpoxyController: IndexEpoxyController
    private lateinit var indexRecyclerView: RecyclerView
    private val indexViewModel: IndexViewModel by viewModel()

    private val email by lazy { "example1@gmail.com" }

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

        indexViewModel.fetch(email)

        indexEpoxyController = IndexEpoxyController(
            onClick = ::navigateToShow,
            noPasswordGroupMessage = getString(R.string.index_no_password_group_message)
        )

        indexRecyclerView = binding.indexFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = indexEpoxyController.adapter
        }



        binding.indexFragmentFab.setOnClickListener {

            showDialogIfNotDrew(tag) {
                IndexCreatePasswordGroupDialogFragment.create(
                    onSubmit = { title, shouldNavigateToEdit ->
                        indexViewModel.createPasswordGroup(email, title, shouldNavigateToEdit)
                    },
                    onCancel = {}
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            indexViewModel.uiState.collect { indexUiState ->

                indexUiState.passwordGroupsState.consume(
                    success = indexEpoxyController::setData,
                    failure = ::showErrorMessage,
                    loading = {},
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            indexViewModel.indexRefreshEvent.collect {
                when(it.navigateToEditEvent) {
                    is NavigateToEditEvent.True -> navigateToEdit(it.navigateToEditEvent.passwordGroupId)
                    is NavigateToEditEvent.False -> indexViewModel.fetch(email)
                }
            }
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

    private fun showDialogIfNotDrew(tag: String?, builder: () -> DialogFragment){
        if(notDrewDialogWithThisTag(tag)) builder().showNow(requireActivity().supportFragmentManager, tag)
    }
    private fun notDrewDialogWithThisTag(tag: String?) =
        (requireActivity().supportFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dialog == null
}

