package com.wsr.edit

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.wsr.R
import com.wsr.databinding.FragmentEditBinding
import com.wsr.state.consume
import com.wsr.utils.launchInLifecycleScope
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

        editEpoxyController = EditEpoxyController(editViewModel::updateName, editViewModel::updatePassword)

        editRecyclerView = binding.editFragmentRecyclerView.apply {
            setHasFixedSize(true)
            adapter = editEpoxyController.adapter
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            editViewModel.uiState.collect { editUiState ->

                editUiState.titleState.consume(
                    success = { (requireActivity() as AppCompatActivity).supportActionBar?.title = it },
                    failure = {
                        Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_LONG,
                        ).show()
                    },
                    loading = {},
                )

                editUiState.passwordsState.consume(
                    success = { editEpoxyController.setData(it) },
                    failure = {
                        Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_LONG,
                        ).show()
                    },
                    loading = {},
                )
            }
        }
    }
}