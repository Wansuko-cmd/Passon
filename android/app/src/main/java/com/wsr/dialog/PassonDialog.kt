package com.wsr.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.wsr.databinding.DialogButtonsBinding
import com.wsr.databinding.DialogMainBinding
import com.wsr.dialog.BundleValue.Companion.getValue
import com.wsr.dialog.BundleValue.Companion.putValue
import com.wsr.utils.autoCleared

class PassonDialog : DialogFragment() {

    private var binding: DialogMainBinding by autoCleared()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogMainBinding.inflate(requireActivity().layoutInflater)

        arguments.getValue<List<(LayoutInflater) -> ViewDataBinding>>(Argument.BINDING_ITEMS)
            ?.forEach { binding.dialogMainLinearLayout.addView(it.invoke(requireActivity().layoutInflater).root) }

        arguments
            .getValue<(LayoutInflater) -> DialogButtonsBinding>(Argument.BUTTONS_BINDING)
            ?.invoke(requireActivity().layoutInflater)
            ?.apply {
                dialogPositiveButton.setOnClickListener {
                    val bundleAttachable = arguments.getValue<List<BundleAttachable>>(Argument.BUNDLE_ATTACHABLE)
                    val bundle = Bundle().apply {
                        bundleAttachable?.forEach { this.putValue(it.key, it.block()) }
                    }
                    arguments.getValue<DialogFragment.(Bundle) -> Unit>(Argument.POSITIVE_BUTTON)
                        ?.let { block ->
                            this@PassonDialog.block(bundle)
                            dismiss()
                        }
                }
                dialogNegativeButton.setOnClickListener {
                    val bundleAttachable = arguments.getValue<List<BundleAttachable>>(Argument.BUNDLE_ATTACHABLE)
                    val bundle = Bundle().apply {
                        bundleAttachable?.forEach { this.putValue(it.key, it.block()) }
                    }
                    arguments.getValue<DialogFragment.(Bundle) -> Unit>(Argument.NEGATIVE_BUTTON)
                        ?.let { block ->
                            this@PassonDialog.block(bundle)
                            dismiss()
                        }
                }
            }
            ?.also { binding.dialogMainLinearLayout.addView(it.root) }

        return AlertDialog.Builder(requireActivity()).apply { setView(binding.root) }.create()
    }

    companion object {
        fun builder() = Builder()
    }
}
