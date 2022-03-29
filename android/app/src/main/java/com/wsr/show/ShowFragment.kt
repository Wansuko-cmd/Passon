package com.wsr.show

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.wsr.R
import com.wsr.databinding.FragmentShowBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.ext.sharedViewModel
import com.wsr.ext.showDialogIfNotDrawn
import com.wsr.show.dialog.ShowDeletePasswordGroupDialogFragment
import com.wsr.state.consume
import org.koin.androidx.viewmodel.ViewModelOwner

class ShowFragment : Fragment(R.layout.fragment_show) {

    private val showViewModel: ShowViewModel by sharedViewModel(owner = { ViewModelOwner.from(this) })

    private val args: ShowFragmentArgs by navArgs()
    private val passwordGroupId by lazy { args.passwordGroupId }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.show_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.show_menu_delete -> {
                showDialogIfNotDrawn(tag) {
                    ShowDeletePasswordGroupDialogFragment.create(passwordGroupId)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        val binding = FragmentShowBinding.bind(view)

        showViewModel.fetch(passwordGroupId)

        val showEpoxyController = ShowEpoxyController(
            onClickShowPassword = { showViewModel.updateShowPassword(it) },
            onClickPasswordCopy = { writePasswordToClipboard(it.password) },
        )

        binding.showFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = showEpoxyController.adapter
        }

        binding.showFragmentFab.setOnClickListener { navigateToEdit(passwordGroupId) }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            showViewModel.uiState.collect { showUiState ->

                showUiState.passwordGroup.consume(
                    success = showEpoxyController::setFirstData,
                    failure = ::showErrorMessage,
                    loading = { /* do nothing */ },
                )

                showUiState.passwordItems.consume(
                    success = { passwordItems ->
                        binding.showFragmentNoPasswordMessage.visibility = if (passwordItems.isEmpty()) View.VISIBLE else View.GONE
                        showEpoxyController.setSecondData(passwordItems)
                    },
                    failure = ::showErrorMessage,
                    loading = { /* do nothing */ },
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            showViewModel.navigateToIndexEvent.collect {
                findNavController().popBackStack()
            }
        }
    }

    private fun writePasswordToClipboard(text: String) {
        val clip = ClipData.newPlainText("password", text)
        val clipBoardManager =
            requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipBoardManager.setPrimaryClip(clip)

        Toast.makeText(
            context,
            getString(R.string.show_toast_on_copy_message),
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun navigateToEdit(passwordGroupId: String) {
        val action = ShowFragmentDirections.actionShowFragmentToEditFragment(passwordGroupId)
        findNavController().navigate(action)
    }

    private fun showErrorMessage(errorShowUiState: ErrorShowUiState) =
        Toast.makeText(
            context,
            errorShowUiState.message,
            Toast.LENGTH_LONG,
        ).show()
}
