package com.wsr.show

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.wsr.databinding.FragmentShowBinding
import com.wsr.state.State
import com.wsr.utils.launchInLifecycleScope
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.bind
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
        _binding = FragmentShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showViewModel.fetchPasswords(passwordGroupId)

        showEpoxyController = ShowEpoxyController()

        showRecyclerView = binding.showFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = showEpoxyController.adapter
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            showViewModel.uiState.collect { showUiState ->
                when(showUiState.passwordsState) {
                    is State.Loading -> {}
                    is State.Success -> {
                        Log.d("OK", showUiState.passwordsState.value.toString())
                        showEpoxyController.setData(showUiState.passwordsState.value)
                    }
                    is State.Failure -> {
                        Toast.makeText(
                            context,
                            showUiState.passwordsState.value.message,
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
            }
        }
    }
}