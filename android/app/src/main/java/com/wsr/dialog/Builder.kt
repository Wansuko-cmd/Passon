package com.wsr.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.wsr.R
import com.wsr.databinding.*
import com.wsr.dialog.Builder.Complete.Companion.toComplete
import com.wsr.dialog.BundleValue.Companion.putValue

class Builder {
    private val bindingItems = mutableListOf<(LayoutInflater) -> ViewDataBinding>()
    private val bundleAttachable = mutableListOf<BundleAttachable>()

    fun setTitle(title: String): Builder {
        bindingItems.add { inflater ->
            DataBindingUtil.inflate<DialogTitleBinding>(
                inflater,
                R.layout.dialog_title,
                null,
                true,
            ).apply { dialogTitle.text = title }
        }

        return this
    }

    fun setEditText(key: String, hint: String = ""): Builder {
        bindingItems.add { inflater ->
            DataBindingUtil.inflate<DialogEditTextBinding>(
                inflater,
                R.layout.dialog_edit_text,
                null,
                true,
            )
                .apply { dialogEditText.hint = hint }
                .also {
                    bundleAttachable.add(
                        BundleAttachable(key) {
                            it.dialogEditText.text.toString()
                        }
                    )
                }
        }

        return this
    }

    fun setCheckboxWithText(key: String, text: String): Builder {
        bindingItems.add { inflater ->
            DataBindingUtil.inflate<DialogCheckboxWithTextBinding>(
                inflater,
                R.layout.dialog_checkbox_with_text,
                null,
                true,
            )
                .apply { dialogCheckboxWithText.text = text }
                .also {
                    bundleAttachable.add(
                        BundleAttachable(key) {
                            it.dialogCheckboxWithText.isChecked.toString()
                        }
                    )
                }
        }

        return this
    }

    fun setButtons(
        positive: (Bundle) -> Unit,
        negative: (Bundle) -> Unit
    ): Complete = toComplete { inflater: LayoutInflater ->
        val binding = DataBindingUtil.inflate<DialogButtonsBinding>(
            inflater,
            R.layout.dialog_buttons,
            null,
            true,
        )

        return@toComplete { bundle: Lazy<Bundle> ->
            binding.dialogPositiveButton.setOnClickListener {
                positive(bundle.value)
                dismiss()
            }
            binding.dialogNegativeButton.setOnClickListener {
                negative(bundle.value)
                dismiss()
            }
            binding.root
        }
    }


    fun setDangerButtons(
        positive: (Bundle) -> Unit,
        negative: (Bundle) -> Unit,
    ) = toComplete { inflater: LayoutInflater ->
        val binding = DataBindingUtil.inflate<DialogDangerButtonsBinding>(
            inflater,
            R.layout.dialog_danger_buttons,
            null,
            true
        )

        return@toComplete { bundle: Lazy<Bundle> ->
            binding.dialogDangerPositiveButton.setOnClickListener {
                positive(bundle.value)
                dismiss()
            }
            binding.dialogDangerNegativeButton.setOnClickListener {
                negative(bundle.value)
                dismiss()
            }
            binding.root
        }
    }

    class Complete private constructor(
        private val bindingItems: List<(LayoutInflater) -> ViewDataBinding>,
        private val bundleAttachable: List<BundleAttachable>,
        private val buttonsBinding: (LayoutInflater) -> DialogFragment.(Lazy<Bundle>) -> View,
    ) {
        fun build(): PassonDialog = PassonDialog().apply {
            arguments = Bundle().apply {
                putValue(Argument.BINDING_ITEMS, bindingItems)
                putValue(Argument.BUNDLE_ATTACHABLE, bundleAttachable)
                putValue(Argument.BUTTONS_BINDING, buttonsBinding)
            }
        }

        companion object {
            fun Builder.toComplete(
                buttonsBinding: (LayoutInflater) -> DialogFragment.(Lazy<Bundle>) -> View,
            ) = Complete(bindingItems, bundleAttachable, buttonsBinding)
        }
    }
}
