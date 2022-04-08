package com.wsr.dialog

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.wsr.R
import com.wsr.databinding.DialogButtonsBinding
import com.wsr.databinding.DialogCheckboxWithTextBinding
import com.wsr.databinding.DialogDangerButtonsBinding
import com.wsr.databinding.DialogEditTextBinding
import com.wsr.databinding.DialogMessageBinding
import com.wsr.databinding.DialogTitleBinding
import com.wsr.dialog.Builder.Complete.Companion.toComplete
import com.wsr.dialog.BundleValue.Companion.putValue
import com.wsr.layout.InputType

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

    fun setMessage(message: String): Builder {
        bindingItems.add { inflater ->
            DataBindingUtil.inflate<DialogMessageBinding>(
                inflater,
                R.layout.dialog_message,
                null,
                true,
            ).apply { dialogMessage.text = message }
        }

        return this
    }

    fun setEditText(
        key: String,
        hint: String = "",
        text: String = "",
        inputType: InputType = InputType.Text,
    ): Builder {
        bindingItems.add { inflater ->
            DataBindingUtil.inflate<DialogEditTextBinding>(
                inflater,
                R.layout.dialog_edit_text,
                null,
                true,
            )
                .apply {
                    dialogEditText.hint = hint
                    dialogEditText.setText(text)
                    dialogEditText.inputType = inputType.value
                }
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
        positiveText: String,
        positive: DialogFragment.(Bundle) -> Unit,
        negativeText: String,
        negative: DialogFragment.(Bundle) -> Unit,
    ): Complete {
        val block = { inflater: LayoutInflater ->
            DataBindingUtil.inflate<DialogButtonsBinding>(
                inflater,
                R.layout.dialog_buttons,
                null,
                true,
            )
                .apply {
                    dialogPositiveButton.text = positiveText
                    dialogNegativeButton.text = negativeText
                }
                .let {
                    ButtonsBinding(
                        binding = it,
                        positive = it.dialogPositiveButton,
                        negative = it.dialogNegativeButton,
                    )
                }
        }

        return toComplete(block, positive, negative)
    }

    fun setDangerButtons(
        positiveText: String,
        positive: DialogFragment.(Bundle) -> Unit,
        negativeText: String,
        negative: DialogFragment.(Bundle) -> Unit,
    ): Complete {
        val block = { inflater: LayoutInflater ->
            DataBindingUtil.inflate<DialogDangerButtonsBinding>(
                inflater,
                R.layout.dialog_danger_buttons,
                null,
                true,
            )
                .apply {
                    dialogDangerPositiveButton.text = positiveText
                    dialogDangerNegativeButton.text = negativeText
                }
                .let {
                    ButtonsBinding(
                        binding = it,
                        positive = it.dialogDangerPositiveButton,
                        negative = it.dialogDangerNegativeButton
                    )
                }
        }

        return toComplete(block, positive, negative)
    }

    class Complete private constructor(
        private val bindingItems: List<(LayoutInflater) -> ViewDataBinding>,
        private val bundleAttachable: List<BundleAttachable>,
        private val buttonsBinding: (LayoutInflater) -> ButtonsBinding,
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
                buttonsBinding: (LayoutInflater) -> ButtonsBinding,
                positive: DialogFragment.(Bundle) -> Unit,
                negative: DialogFragment.(Bundle) -> Unit,
            ) = Complete(bindingItems, bundleAttachable, buttonsBinding, positive, negative)
        }
    }
}
