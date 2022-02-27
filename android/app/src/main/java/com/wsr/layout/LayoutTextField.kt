package com.wsr.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.wsr.R
import com.wsr.databinding.LayoutTextFieldBinding

class LayoutTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTextFieldBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_text_field,
            this,
            true
        )
    }


    companion object {

        @BindingAdapter("text")
        @JvmStatic
        fun setText(view: LayoutTextField, text: String) =
            view.binding.layoutTextFieldTextInput.setText(text)

        @BindingAdapter("hint")
        @JvmStatic
        fun setHint(view: LayoutTextField, hint: String) {
            view.binding.layoutTextFieldText.hint = hint
        }

        @BindingAdapter("afterTextChanged")
        @JvmStatic
        fun setOnTextChanged(view: LayoutTextField, afterTextChanged: AfterTextChanged) =
            view.binding.layoutTextFieldTextInput.addTextChangedListener { afterTextChanged.block(it.toString()) }

        @BindingAdapter("enabled")
        @JvmStatic
        fun setEnabled(view: LayoutTextField, enabled: Boolean) {
            view.isEnabled = enabled
            view.binding.layoutTextFieldTextInput.isEnabled = enabled
        }

        @BindingAdapter("inputType")
        @JvmStatic
        fun setInputType(view: LayoutTextField, inputType: InputType) {
            view.binding.layoutTextFieldTextInput.inputType = inputType.value
        }
    }
}