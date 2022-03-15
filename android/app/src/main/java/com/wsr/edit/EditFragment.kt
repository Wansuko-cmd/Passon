package com.wsr.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.wsr.R
import com.wsr.databinding.FragmentEditBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.state.consume
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditFragment : Fragment() {

    private lateinit var _binding: FragmentEditBinding
    private val binding get() = _binding

    private lateinit var editEpoxyController: EditEpoxyController
    private lateinit var editRecyclerView: RecyclerView
    private val editViewModel: EditViewModel by viewModel()

    private val args: EditFragmentArgs by navArgs()
    private val passwordGroupId by lazy { args.passwordGroupId }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editViewModel.fetch(passwordGroupId)

        editEpoxyController = EditEpoxyController(
            afterTitleChanged = editViewModel::updateTitle,
            afterRemarkChanged = editViewModel::updateRemark,
            afterNameChanged = editViewModel::updateName,
            afterPasswordChanged = editViewModel::updatePassword,
            onClickAddPasswordButton = { editViewModel.createPassword(passwordGroupId) },
        )

        editRecyclerView = binding.editFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = editEpoxyController.adapter
        }

        binding.editFragmentFab.setOnClickListener {
            launchInLifecycleScope(Lifecycle.State.STARTED) {
                editViewModel.save(passwordGroupId).consume(
                    success = {
                        Toast.makeText(
                            context,
                            getString(R.string.edit_toast_on_save_message),
                            Toast.LENGTH_LONG,
                        ).show()
                    },
                    failure = this@EditFragment::showErrorMessage,
                    loading = {},
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            editViewModel.uiState.collect { editUiState ->

                editUiState.titleState.consume(
                    success = {
                        (requireActivity() as AppCompatActivity).supportActionBar?.title = it
                    },
                    failure = ::showErrorMessage,
                    loading = {},
                )

                editUiState.contents.passwordGroup.consume(
                    success = editEpoxyController::initializeFirstData,
                    failure = ::showErrorMessage,
                    loading = {},
                )

                editUiState.contents.passwords.consume(
                    success = editEpoxyController::initializeSecondData,
                    failure = ::showErrorMessage,
                    loading = {},
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            editViewModel.editRefreshEvent.collect {
                editEpoxyController.refresh(it.passwordGroup, it.passwords)
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
