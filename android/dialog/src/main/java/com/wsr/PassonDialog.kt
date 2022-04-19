package com.wsr

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
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
            .getValue<(LayoutInflater) -> DialogFragment.(Lazy<Bundle>) -> View>(Argument.BUTTONS_BINDING)
            ?.invoke(requireActivity().layoutInflater)
            ?.invoke(
                this@PassonDialog,
                lazy {
                    val bundleAttachable =
                        arguments.getValue<List<BundleAttachable>>(Argument.BUNDLE_ATTACHABLE)
                    Bundle().apply {
                        bundleAttachable?.forEach { this.putValue(it.key, it.block()) }
                    }
                }
            )
            ?.also { binding.dialogMainLinearLayout.addView(it) }

        return AlertDialog.Builder(requireActivity()).apply { setView(binding.root) }.create()
    }

    companion object {
        fun builder() = Builder()
    }
}
