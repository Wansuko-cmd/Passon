package com.wsr.dialog

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.wsr.R
import com.wsr.databinding.DialogButtonsBinding
import com.wsr.databinding.DialogCheckboxWithTextBinding
import com.wsr.databinding.DialogEditTextBinding
import com.wsr.databinding.DialogTitleBinding
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

    fun setButtons(positive: DialogFragment.(Bundle) -> Unit, negative: DialogFragment.(Bundle) -> Unit): Complete {
        val block = { inflater: LayoutInflater ->
            DataBindingUtil.inflate<DialogButtonsBinding>(
                inflater,
                R.layout.dialog_buttons,
                null,
                true,
            )
        }

        return toComplete(block, positive, negative)
    }

    class Complete private constructor(
        private val bindingItems: List<(LayoutInflater) -> ViewDataBinding>,
        private val bundleAttachable: List<BundleAttachable>,
        private val buttonsBinding: (LayoutInflater) -> DialogButtonsBinding,
        private val positive: DialogFragment.(Bundle) -> Unit,
        private val negative: DialogFragment.(Bundle) -> Unit,
    ) {
        fun build(): PassonDialog = PassonDialog().apply {
            arguments = Bundle().apply {
                putValue(Argument.BINDING_ITEMS, bindingItems)
                putValue(Argument.BUNDLE_ATTACHABLE, bundleAttachable)
                putValue(Argument.BUTTONS_BINDING, buttonsBinding)
                putValue(Argument.POSITIVE_BUTTON, positive)
                putValue(Argument.NEGATIVE_BUTTON, negative)
            }
        }

        companion object {
            fun Builder.toComplete(
                buttonsBinding: (LayoutInflater) -> DialogButtonsBinding,
                positive: DialogFragment.(Bundle) -> Unit,
                negative: DialogFragment.(Bundle) -> Unit,
            ) = Complete(bindingItems, bundleAttachable, buttonsBinding, positive, negative)
        }
    }
}
