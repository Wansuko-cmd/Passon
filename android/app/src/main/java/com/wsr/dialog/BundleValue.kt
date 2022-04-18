package com.wsr.dialog

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import java.io.Serializable

class BundleValue<T>(private val value: T) : Serializable {
    companion object {
        fun <T> Bundle.putValue(key: Argument, value: T) = this.putSerializable(key.name, BundleValue(value))
        fun <T> Bundle.putValue(key: String, value: T) = this.putSerializable(key, BundleValue(value))

        @Suppress("UNCHECKED_CAST")
        fun <T> Bundle?.getValue(key: Argument): T? = (this?.getSerializable(key.name) as? BundleValue<T>)?.value
        @Suppress("UNCHECKED_CAST")
        fun <T> Bundle?.getValue(key: String): T? = (this?.getSerializable(key) as? BundleValue<T>)?.value
    }
}

enum class Argument {
    BINDING_ITEMS,
    BUNDLE_ATTACHABLE,
    BUTTONS_BINDING,
}

data class BundleAttachable(val key: String, val block: () -> Any)

data class ButtonsBinding(val binding: ViewDataBinding, val positive: View, val negative: View)
