package com.wsr.edit

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wsr.R
import com.wsr.databinding.FragmentEditBinding
import com.wsr.utils.ext.launchInLifecycleScope
import com.wsr.utils.consume
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditFragment : Fragment(R.layout.fragment_edit) {

    private val editViewModel: EditViewModel by viewModel()

    private val args: EditFragmentArgs by navArgs()
    private val passwordGroupId by lazy { args.passwordGroupId }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (editViewModel.uiState.value.edited) {
                    Toast.makeText(requireContext(), getString(R.string.edit_leave_page_waring), Toast.LENGTH_LONG).show()
                    editViewModel.resetEdited()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = FragmentEditBinding.bind(view)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (editViewModel.uiState.value.edited) {
                Toast.makeText(requireContext(), getString(R.string.edit_leave_page_waring), Toast.LENGTH_LONG).show()
                editViewModel.resetEdited()
                return@addCallback
            }
            findNavController().popBackStack()
        }

        editViewModel.fetch(passwordGroupId)

        val editEpoxyController = EditEpoxyController(
            afterTitleChanged = editViewModel::updateTitle,
            afterRemarkChanged = editViewModel::updateRemark,
            afterNameChanged = editViewModel::updateName,
            afterPasswordChanged = editViewModel::updatePassword,
            onClickDeletePasswordItemButton = { editViewModel.deletePasswordItem(it) },
            onClickAddPasswordButton = { editViewModel.createPasswordItem(passwordGroupId) },
            onClickShowPassword = { editViewModel.updateShowPassword(it) },
        )

        binding.editFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = editEpoxyController.adapter
        }

        binding.editFragmentFab.setOnClickListener {
            launchInLifecycleScope(Lifecycle.State.STARTED) {
                editViewModel.sync(passwordGroupId).consume(
                    success = {
                        Toast.makeText(
                            context,
                            getString(R.string.edit_toast_on_save_message),
                            Toast.LENGTH_SHORT,
                        ).show()
                    },
                    failure = this@EditFragment::showErrorMessage,
                    loading = { /* do nothing */ },
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            editViewModel.uiState.collect { editUiState ->

                editUiState.passwordGroup.consume(
                    success = editEpoxyController::initializeFirstData,
                    failure = ::showErrorMessage,
                    loading = { /* do nothing */ },
                )

                editUiState.passwordItems.consume(
                    success = editEpoxyController::initializeSecondData,
                    failure = ::showErrorMessage,
                    loading = { /* do nothing */ },
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            editViewModel.editRefreshEvent.collect {
                editEpoxyController.refresh(it.passwordGroup, it.passwordItems)
            }
        }
    }

    private fun showErrorMessage(errorEditUiState: ErrorEditUiState) =
        Toast.makeText(
            context,
            errorEditUiState.message,
            Toast.LENGTH_LONG,
        ).show()
}
