package com.wsr.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.wsr.layout.R
import com.wsr.layout.databinding.LayoutTextFieldBinding

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

    private fun setText(text: String) {
        binding.layoutTextFieldTextInput.setText(text)
    }

    private fun setHint(hint: String) {
        binding.layoutTextFieldTextInput.hint = hint
    }

    private fun setOnTextChanged(afterTextChanged: AfterTextChanged) {
        binding.layoutTextFieldTextInput.addTextChangedListener { afterTextChanged.block(it.toString()) }
    }

    override fun setEnabled(enabled: Boolean) {
        binding.layoutTextFieldTextInput.isEnabled = enabled
    }

    private fun setInputType(inputType: InputType) {
        binding.layoutTextFieldTextInput.inputType = inputType.value
    }

    fun onEnterClicked(block: () -> Unit) {
        binding.layoutTextFieldTextInput.setOnEditorActionListener { _, actionId, _ ->
            val isDone = actionId == EditorInfo.IME_ACTION_DONE
            if (isDone) block()

            isDone
        }
    }

    companion object {

        @BindingAdapter("text")
        @JvmStatic
        fun setText(view: LayoutTextField, text: String) = view.setText(text)

        @BindingAdapter("hint")
        @JvmStatic
        fun setHint(view: LayoutTextField, hint: String) = view.setHint(hint)

        @BindingAdapter("afterTextChanged")
        @JvmStatic
        fun setOnTextChanged(view: LayoutTextField, afterTextChanged: AfterTextChanged) =
            view.setOnTextChanged(afterTextChanged)

        @BindingAdapter("enabled")
        @JvmStatic
        fun setEnabled(view: LayoutTextFieldWithIcon, enabled: Boolean) {
            view.isEnabled = enabled
        }

        @BindingAdapter("inputType")
        @JvmStatic
        fun setInputType(view: LayoutTextField, inputType: InputType) = view.setInputType(inputType)
    }
}
