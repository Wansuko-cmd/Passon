package com.wsr.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wsr.R
import com.wsr.databinding.DialogEditTextBinding
import com.wsr.databinding.DialogTitleBinding
import com.wsr.dialog.BundleValue.Companion.putValue

class Builder(context: Context) {
    private val inflater = LayoutInflater.from(context)

    private val bindingItems = mutableListOf<ViewDataBinding>()
    private val bundleAttachable = mutableListOf<Lazy<BundleAttachable>>()
    private lateinit var positive: (Bundle) -> Unit
    private lateinit var negative: (Bundle) -> Unit

    fun setTitle(title: String): Builder {
        DataBindingUtil.inflate<DialogTitleBinding>(
            inflater,
            R.layout.dialog_title,
            null,
            true,
        )
            .apply { dialogTitle.text = title }
            .also { bindingItems.add(it) }
        return this
    }

    fun setEditText(key: String): Builder {
        DataBindingUtil.inflate<DialogEditTextBinding>(
            inflater,
            R.layout.dialog_edit_text,
            null,
            true,
        )
            .also { bindingItems.add(it) }
            .also {
                bundleAttachable.add(
                    lazy {
                        BundleAttachable(key) {
                            it.dialogEditText.text.toString()
                        }
                    }
                )
            }
        return this
    }

    fun setButtons(positive: (Bundle) -> Unit, negative: (Bundle) -> Unit) = Complete(positive, negative)

    inner class Complete(positive: (Bundle) -> Unit, negative: (Bundle) -> Unit) {
        fun build(): PassonDialog = PassonDialog().apply {
            arguments = Bundle().apply {
                putValue(Argument.BINDING_ITEMS, bindingItems)
                putValue(Argument.BUNDLE_ATTACHABLE, bundleAttachable)
                putValue(Argument.POSITIVE_BUTTON, positive)
                putValue(Argument.NEGATIVE_BUTTON, negative)
            }
        }
    }
}
