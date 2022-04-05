package com.wsr.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.wsr.R
import com.wsr.databinding.DialogEditTextBinding
import com.wsr.databinding.DialogMainBinding
import com.wsr.databinding.DialogTitleBinding
import com.wsr.dialog.BundleValue.Companion.getValue
import com.wsr.dialog.BundleValue.Companion.putValue
import java.io.Serializable
import kotlin.reflect.KClass

class PassonDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogMainBinding.inflate(requireActivity().layoutInflater)

        arguments.getValue<List<ViewDataBinding>>(Argument.BINDING_ITEMS)
            ?.forEach { binding.dialogMainLinearLayout.addView(it.root) }


        return AlertDialog.Builder(requireActivity()).apply { setView(binding.root) }.create()
    }

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
                .also { bundleAttachable.add(lazy { BundleAttachable(key, String::class) { it.dialogEditText.text.toString() }) }}
            return this
        }

        fun build(): PassonDialog = PassonDialog().apply {
            val bundle = Bundle()
            bundle.putValue<List<ViewDataBinding>>(Argument.BINDING_ITEMS, bindingItems)
        }
    }
}

private data class BundleAttachable(val key: String, val type: KClass<out Any>, val block: () -> Any)

private enum class Argument {
    BINDING_ITEMS,
    BUNDLE_ATTACHABLE;
}

private class BundleValue<T>(private val value: T) : Serializable {
    companion object {
        fun <T> Bundle.putValue(key: Argument, value: T) = this.putSerializable(key.name, BundleValue(value))

        @Suppress("UNCHECKED_CAST")
        fun <T> Bundle?.getValue(key: Argument): T? = (this?.getSerializable(key.name) as? BundleValue<T>)?.value
    }
}
