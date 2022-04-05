package com.wsr.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wsr.R
import com.wsr.databinding.DialogButtonsBinding
import com.wsr.databinding.DialogEditTextBinding
import com.wsr.databinding.DialogTitleBinding
import com.wsr.dialog.Builder.Complete.Companion.toComplete
import com.wsr.dialog.BundleValue.Companion.putValue

class Builder(context: Context) {
    private val bindingItems = mutableListOf<Lazy<(LayoutInflater) -> ViewDataBinding>>()
    private val bundleAttachable = mutableListOf<Lazy<BundleAttachable>>()

    fun setTitle(title: String): Builder {
        bindingItems.add(lazy { { inflater ->
            DataBindingUtil.inflate<DialogTitleBinding>(
                inflater,
                R.layout.dialog_title,
                null,
                true,
            ).apply { dialogTitle.text = title }
        } })

        return this
    }

    fun setEditText(key: String, hint: String = ""): Builder {
        bindingItems.add(lazy { { inflater ->
            DataBindingUtil.inflate<DialogEditTextBinding>(
                inflater,
                R.layout.dialog_edit_text,
                null,
                true,
            )
                .apply { dialogEditText.hint = hint }
                .also {
                    bundleAttachable.add(
                        lazy {
                            BundleAttachable(key) {
                                it.dialogEditText.text.toString()
                            }
                        }
                    )
                }
        } })

        return this
    }

    fun setButtons(positive: (Bundle) -> Unit, negative: (Bundle) -> Unit): Complete {
        val block = lazy<(LayoutInflater) -> DialogButtonsBinding> {
            { inflater ->
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.dialog_buttons,
                    null,
                    true,
                )
            }
        }
        bindingItems.add(block)

        return toComplete(block, positive, negative)
    }

    class Complete private constructor(
        private val bindingItems: List<Lazy<(LayoutInflater) -> ViewDataBinding>>,
        private val bundleAttachable: List<Lazy<BundleAttachable>>,
        private val buttonsBinding: Lazy<(LayoutInflater) -> DialogButtonsBinding>,
        private val positive: (Bundle) -> Unit,
        private val negative: (Bundle) -> Unit,
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
                buttonsBinding: Lazy<(LayoutInflater) -> DialogButtonsBinding>,
                positive: (Bundle) -> Unit,
                negative: (Bundle) -> Unit,
            ) = Complete(bindingItems, bundleAttachable, buttonsBinding, positive, negative)
        }
    }
}
