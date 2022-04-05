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
import com.wsr.databinding.DialogMainBinding
import com.wsr.databinding.DialogTitleBinding
import com.wsr.dialog.BundleValue.Companion.getValue
import com.wsr.dialog.BundleValue.Companion.putValue
import java.io.Serializable

class PassonDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogMainBinding.inflate(requireActivity().layoutInflater)

        arguments.getValue<List<ViewDataBinding>>(Argument.BINDING_ITEMS)
            ?.forEach { binding.dialogMainLinearLayout.addView(it.root) }

        return AlertDialog.Builder(requireActivity()).apply { setView(binding.root) }.create()
    }

    class Builder(private val context: Context) {
        private val inflater = LayoutInflater.from(context)
        private val bindingItems = mutableListOf<ViewDataBinding>()

        fun setTitle(title: String): Builder {
            DataBindingUtil.inflate<DialogTitleBinding>(
                inflater,
                R.layout.dialog_title,
                null,
                true,
            ).apply { dialogTitle.text = title }
                .also { bindingItems.add(it) }
            return this
        }

        fun build(): PassonDialog = PassonDialog().apply {
            val bundle = Bundle()
            bundle.putValue<List<ViewDataBinding>>(Argument.BINDING_ITEMS, bindingItems)
        }
    }
}

private enum class Argument {
    BINDING_ITEMS;
}

private class BundleValue<T>(private val value: T) : Serializable {
    companion object {
        fun <T> Bundle.putValue(key: Argument, value: T) = this.putSerializable(key.name, BundleValue(value))

        @Suppress("UNCHECKED_CAST")
        fun <T> Bundle?.getValue(key: Argument): T? = (this?.getSerializable(key.name) as? BundleValue<T>)?.value
    }
}
