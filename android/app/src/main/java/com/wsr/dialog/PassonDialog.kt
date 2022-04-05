package com.wsr.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
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

        arguments.getValue<List<Lazy<(LayoutInflater) -> ViewDataBinding>>>(Argument.BINDING_ITEMS)
            ?.forEach { binding.dialogMainLinearLayout.addView(it.value.invoke(requireActivity().layoutInflater).root) }

        val bundleAttachable = arguments.getValue<List<Lazy<BundleAttachable>>>(Argument.BUNDLE_ATTACHABLE)
        val bundle = Bundle().apply {
            bundleAttachable?.forEach { this.putValue(it.value.key, it.value.block()) }
        }
        arguments
            .getValue<Lazy<(LayoutInflater) -> DialogButtonsBinding>>(Argument.BUTTONS_BINDING)
            ?.value
            ?.invoke(requireActivity().layoutInflater)
            ?.apply {

                dialogPositiveButton.setOnClickListener {
                    arguments.getValue<(Bundle) -> Unit>(Argument.POSITIVE_BUTTON)
                        ?.let { it1 -> it1(bundle) }
                }
                dialogNegativeButton.setOnClickListener {
                    arguments.getValue<(Bundle) -> Unit>(Argument.NEGATIVE_BUTTON)
                        ?.let { it1 -> it1(bundle) }
                }
            }

        return AlertDialog.Builder(requireActivity()).apply { setView(binding.root) }.create()
    }

    companion object {
        fun builder(context: Context) = Builder(context)
    }
}
