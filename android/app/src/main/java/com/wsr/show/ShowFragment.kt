package com.wsr.show

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.wsr.R
import com.wsr.databinding.FragmentShowBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.state.consume
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowFragment : Fragment() {

    private lateinit var _binding: FragmentShowBinding
    private val binding get() = _binding

    private lateinit var showEpoxyController: ShowEpoxyController
    private lateinit var showRecyclerView: RecyclerView
    private val showViewModel: ShowViewModel by viewModel()

    private val args: ShowFragmentArgs by navArgs()
    private val passwordGroupId by lazy { args.passwordGroupId }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        _binding = FragmentShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.show_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showFragmentFab.setOnClickListener { navigateToEdit(passwordGroupId) }

        showViewModel.fetch(passwordGroupId)

        showEpoxyController = ShowEpoxyController(
            onClickShowPassword = { showViewModel.changePasswordState(it.id) },
            onClickPasswordCopy = { writeToClipboard("password", it.password) },
            noPasswordMessage = getString(R.string.show_no_password_message)
        )

        showRecyclerView = binding.showFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = showEpoxyController.adapter
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            showViewModel.uiState.collect { showUiState ->

                showUiState.titleState.consume(
                    success = {
                        (requireActivity() as AppCompatActivity).supportActionBar?.title = it
                    },
                    failure = ::showErrorMessage,
                    loading = {},
                )

                showUiState.contents.passwordGroup.consume(
                    success = showEpoxyController::setFirstData,
                    failure = ::showErrorMessage,
                    loading = {},
                )

                showUiState.contents.passwords.consume(
                    success = showEpoxyController::setSecondData,
                    failure = ::showErrorMessage,
                    loading = {},
                )
            }
        }
    }

    private fun writeToClipboard(tag: String, text: String) {
        val clip = ClipData.newPlainText(tag, text)
        val clipBoardManager =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipBoardManager.setPrimaryClip(clip)

        Toast.makeText(
            context,
            "コピーしました",
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