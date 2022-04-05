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
import com.wsr.dialog.BundleValue.Companion.putValue

class Builder(context: Context) {
    private val inflater = LayoutInflater.from(context)

    private val bindingItems = mutableListOf<ViewDataBinding>()
    private val bundleAttachable = mutableListOf<Lazy<BundleAttachable>>()

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
                        BundleAttachable(key, String::class) {
                            it.dialogEditText.text.toString()
                        }
                    }
                )
            }
        return this
    }

    fun setButtons(negative: (Bundle) -> Unit, positive: (Bundle) -> Unit) {
        DataBindingUtil.inflate<DialogButtonsBinding>(
            inflater,
            R.layout.dialog_buttons,
            null,
            true,
        )
            .also { bindingItems.add(it) }
    }

    fun build(): PassonDialog = PassonDialog().apply {
        val bundle = Bundle()
        bundle.putValue<List<ViewDataBinding>>(Argument.BINDING_ITEMS, bindingItems)
    }
}