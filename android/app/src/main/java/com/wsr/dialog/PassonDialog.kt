package com.wsr.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.wsr.R
import com.wsr.databinding.DialogButtonsBinding
import com.wsr.databinding.DialogMainBinding
import com.wsr.dialog.BundleValue.Companion.getValue
import com.wsr.dialog.BundleValue.Companion.putValue

class PassonDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogMainBinding.inflate(requireActivity().layoutInflater)

        arguments.getValue<List<ViewDataBinding>>(Argument.BINDING_ITEMS)
            ?.forEach { binding.dialogMainLinearLayout.addView(it.root) }

        val bundleAttachable = arguments.getValue<List<Lazy<BundleAttachable>>>(Argument.BUNDLE_ATTACHABLE)
        val bundle = Bundle().apply {
            bundleAttachable?.forEach { this.putValue(it.value.key, it.value.block()) }
        }
        DataBindingUtil.inflate<DialogButtonsBinding>(
            layoutInflater,
            R.layout.dialog_buttons,
            null,
            true,
        ).apply {
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
}
