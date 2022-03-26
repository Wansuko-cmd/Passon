package com.wsr.show.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.wsr.databinding.DialogShowDeletePasswordGroupBinding
import com.wsr.ext.sharedViewModel
import com.wsr.show.ShowViewModel
import org.koin.androidx.viewmodel.ViewModelOwner

class ShowDeletePasswordGroupDialogFragment : DialogFragment() {

    private val showViewModel by sharedViewModel<ShowViewModel>(owner = {
        ViewModelOwner.from(requireParentFragment())
    })

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val passwordGroupId = arguments?.getString("passwordGroupId")
            ?: throw NoSuchElementException("passwordGroupId does not exist in bundle at ShowDeletePasswordGroupDialogFragment")

        val binding =
            DialogShowDeletePasswordGroupBinding.inflate(requireActivity().layoutInflater).apply {
                dialogShowDeletePasswordGroupOkButton.setOnClickListener {
                    showViewModel.delete(passwordGroupId)
                }
                dialogShowDeletePasswordGroupCancelButton.setOnClickListener { dismiss() }
            }
        return AlertDialog.Builder(requireActivity()).apply { setView(binding.root) }.create()
    }

    companion object {
        fun create(passwordGroupId: String): ShowDeletePasswordGroupDialogFragment {
            return ShowDeletePasswordGroupDialogFragment().apply {
                val bundle = Bundle()
                bundle.putString("passwordGroupId", passwordGroupId)
                arguments = bundle
            }
        }
    }
}
