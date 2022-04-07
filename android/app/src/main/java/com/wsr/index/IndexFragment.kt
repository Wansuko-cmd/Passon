package com.wsr.index

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wsr.R
import com.wsr.databinding.FragmentIndexBinding
import com.wsr.dialog.BundleValue.Companion.getValue
import com.wsr.dialog.PassonDialog
import com.wsr.ext.launchInLifecycleScope
import com.wsr.ext.sharedViewModel
import com.wsr.ext.showDialogIfNotDrawn
import com.wsr.utils.consume
import org.koin.androidx.viewmodel.ViewModelOwner.Companion.from

class IndexFragment : Fragment(R.layout.fragment_index) {

    private val indexViewModel: IndexViewModel by sharedViewModel(owner = { from(this) })

    private val args: IndexFragmentArgs by navArgs()
    private val userId by lazy { args.userId }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.index_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.index_menu_setting -> {
                val action = IndexFragmentDirections.actionIndexFragmentToSettingsFragment(userId)
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentIndexBinding.bind(view)

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            show()
            setDisplayHomeAsUpEnabled(false)
        }

        indexViewModel.fetch(userId)

        val indexEpoxyController = IndexEpoxyController(
            onClick = ::navigateToShow,
            resources = resources,
        )

        binding.indexFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = indexEpoxyController.adapter
        }

        binding.indexFragmentFab.setOnClickListener { showCreatePasswordGroupDialog() }

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
            indexViewModel.indexRefreshEvent.collect { indexViewModel.fetch(userId) }
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

    private fun showCreatePasswordGroupDialog() {
        showDialogIfNotDrawn(tag) {
            PassonDialog.builder()
                .setTitle(getString(R.string.index_create_password_group_dialog_title))
                .setEditText(
                    key = "passwordGroup",
                    hint = getString(R.string.index_create_password_group_dialog_hint)
                )
                .setCheckboxWithText("navigateToEdit", getString(R.string.index_create_password_group_dialog_checkbox_text))
                .setButtons(
                    positive = { bundle ->
                        val passwordGroupName = bundle
                            .getValue<String>("passwordGroup") ?: return@setButtons
                        val navigateToEdit = bundle
                            .getValue<String>("navigateToEdit")
                            ?.toBooleanStrictOrNull() ?: return@setButtons
                        indexViewModel.createPasswordGroup(userId, passwordGroupName, navigateToEdit)
                    },
                    negative = { },
                )
                .build()
        }
    }

    private fun showErrorMessage(errorIndexUiState: ErrorIndexUiState) =
        Toast.makeText(
            context,
            errorIndexUiState.message,
            Toast.LENGTH_LONG,
        ).show()
}
