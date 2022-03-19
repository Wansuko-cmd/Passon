package com.wsr.index.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import com.wsr.databinding.DialogIndexCreatePasswordGroupBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.index.dialog.OnCancel.Companion.getOnCancelInstance
import com.wsr.index.dialog.OnSubmit.Companion.getOnSubmitInstance
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class IndexCreatePasswordGroupDialogFragment : DialogFragment() {

    private val indexCreatePasswordGroupDialogViewModel by viewModel<IndexCreatePasswordGroupDialogViewModel>()
    private lateinit var binding: DialogIndexCreatePasswordGroupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val onSubmit = arguments.getOnSubmitInstance()
        val onCancel = arguments.getOnCancelInstance()

        binding =
            DialogIndexCreatePasswordGroupBinding.inflate(requireActivity().layoutInflater).apply {
                dialogIndexCreatePasswordGroupSubmitButton.setOnClickListener {
                    onSubmit.block(
                        dialogIndexCreatePasswordGroupEditText.text.toString(),
                        indexCreatePasswordGroupDialogViewModel.shouldNavigateToEdit.value,
                    )
                    dismiss()
                }
                dialogIndexCreatePasswordGroupCancelButton.setOnClickListener {
                    onCancel.block()
                    dismiss()
                }
                onClickCheckbox = View.OnClickListener {
                    indexCreatePasswordGroupDialogViewModel.changeChecked()
                }
            }

        return AlertDialog.Builder(requireActivity()).apply { setView(binding.root) }.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            indexCreatePasswordGroupDialogViewModel.shouldNavigateToEdit.collect {
                binding.checked = it
            }
        }
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

private class OnSubmit(val block: (title: String, goToEdit: Boolean) -> Unit = { _, _ -> }) :
    Serializable {
    companion object {
        const val key = "onSubmit"
        fun Bundle?.getOnSubmitInstance() = (this?.getSerializable(key) ?: OnSubmit()) as OnSubmit
    }
}

private class OnCancel(val block: () -> Unit = { /* do nothing */ }) : Serializable {
    companion object {
        const val key = "onCancel"
        fun Bundle?.getOnCancelInstance() = (this?.getSerializable(key) ?: OnCancel) as OnCancel
    }
}
