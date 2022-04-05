package com.wsr.dialog

import android.os.Bundle
import java.io.Serializable
import kotlin.reflect.KClass

class BundleValue<T>(private val value: T) : Serializable {
    companion object {
        fun <T> Bundle.putValue(key: Argument, value: T) = this.putSerializable(key.name, BundleValue(value))

        @Suppress("UNCHECKED_CAST")
        fun <T> Bundle?.getValue(key: Argument): T? = (this?.getSerializable(key.name) as? BundleValue<T>)?.value
    }
}

enum class Argument {
    BINDING_ITEMS,
    BUNDLE_ATTACHABLE;
}

data class BundleAttachable(val key: String, val type: KClass<out Any>, val block: () -> Any)
