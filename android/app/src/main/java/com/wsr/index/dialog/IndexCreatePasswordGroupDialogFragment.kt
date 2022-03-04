package com.wsr.index.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.wsr.databinding.DialogIndexCreatePasswordGroupBinding
import com.wsr.index.dialog.OnCancel.Companion.getOnCancelInstance
import com.wsr.index.dialog.OnSubmit.Companion.getOnSubmitInstance
import com.wsr.layout.AfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class IndexCreatePasswordGroupDialogFragment : DialogFragment() {

    private val indexCreatePasswordGroupDialogViewModel by viewModel<IndexCreatePasswordGroupDialogViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onSubmit = arguments.getOnSubmitInstance()
        val onCancel = arguments.getOnCancelInstance()

        val binding = DialogIndexCreatePasswordGroupBinding.inflate(requireActivity().layoutInflater).apply {
            afterTitleChanged = AfterTextChanged(indexCreatePasswordGroupDialogViewModel::updateTitle)
            onSubmitButton = View.OnClickListener {
                onSubmit.block(
                    indexCreatePasswordGroupDialogViewModel.title,
                    indexCreatePasswordGroupDialogViewModel.goToEdit,
                )
                dismiss()
            }
            onCancelButton = View.OnClickListener { onCancel.block(); dismiss()}
            onCheckbox = View.OnClickListener { indexCreatePasswordGroupDialogViewModel.changeChecked() }
            checked = indexCreatePasswordGroupDialogViewModel.goToEdit
        }

        return AlertDialog.Builder(requireActivity()).apply{ setView(binding.root) }.create()
    }

    companion object {
        fun create(
            onSubmit: (title: String, goToEdit: Boolean) -> Unit,
            onCancel: () -> Unit,
        ): IndexCreatePasswordGroupDialogFragment {
            return IndexCreatePasswordGroupDialogFragment().apply {
                val bundle = Bundle()
                bundle.putSerializable(OnSubmit.key, OnSubmit(onSubmit))
                bundle.putSerializable(OnCancel.key, OnCancel(onCancel))
                arguments = bundle
            }
        }
    }
}

private class OnSubmit(val block: (title: String, goToEdit: Boolean) -> Unit = { _, _ ->}): Serializable {
    companion object {
        const val key = "onSubmit"
        fun Bundle?.getOnSubmitInstance() = (this?.getSerializable(key) ?: OnSubmit()) as OnSubmit
    }
}

private class OnCancel(val block: () -> Unit = {}): Serializable {
    companion object {
        const val key = "onCancel"
        fun Bundle?.getOnCancelInstance() = (this?.getSerializable(key) ?: OnCancel) as OnCancel
    }
}