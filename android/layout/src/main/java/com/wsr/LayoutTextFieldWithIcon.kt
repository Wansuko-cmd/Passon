package com.wsr

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.wsr.databinding.LayoutTextFieldWithIconBinding

class LayoutTextFieldWithIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTextFieldWithIconBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_text_field_with_icon,
            this,
            true
        )
    }

    private fun setText(text: String) {
        binding.layoutTextFieldWithIconTextInput.setText(text)
    }

    private fun setHint(hint: String) {
        binding.layoutTextFieldWithIconTextInput.hint = hint
    }

    private fun setMaxLines(maxLines: Int) {
        binding.layoutTextFieldWithIconTextInput.maxLines = maxLines
    }

    private fun setOnTextChanged(afterTextChanged: AfterTextChanged) {
        binding.layoutTextFieldWithIconTextInput.addTextChangedListener { afterTextChanged.block(it.toString()) }
    }

    override fun setEnabled(enabled: Boolean) {
        binding.layoutTextFieldWithIconTextInput.isEnabled = enabled
    }

    private fun setInputType(inputType: InputType) {
        binding.layoutTextFieldWithIconTextInput.inputType = inputType.value
    }

    private fun setIcon(icon: Drawable?) {
        binding.layoutTextFieldWithIconIcon.setImageDrawable(icon)
    }

    private fun setEndIconDrawable(icon: Drawable) {
        binding.layoutTextFieldWithIconText.endIconDrawable = icon
    }

    private fun setEndIconOnClickListener(listener: OnClickListener) {
        binding.layoutTextFieldWithIconText.setEndIconOnClickListener(listener)
    }

    companion object {

        @BindingAdapter("text")
        @JvmStatic
        fun setText(view: LayoutTextFieldWithIcon, text: String) = view.setText(text)

        @BindingAdapter("hint")
        @JvmStatic
        fun setHint(view: LayoutTextFieldWithIcon, hint: String) = view.setHint(hint)

        @BindingAdapter("maxLines")
        @JvmStatic
        fun setMaxLines(view: LayoutTextFieldWithIcon, maxLines: Int) = view.setMaxLines(maxLines)

        @BindingAdapter("afterTextChanged")
        @JvmStatic
        fun setOnTextChanged(view: LayoutTextFieldWithIcon, afterTextChanged: AfterTextChanged) =
            view.setOnTextChanged(afterTextChanged)

        @BindingAdapter("enabled")
        @JvmStatic
        fun setEnabled(view: LayoutTextFieldWithIcon, enabled: Boolean) {
            view.isEnabled = enabled
        }

        @BindingAdapter("inputType")
        @JvmStatic
        fun setInputType(view: LayoutTextFieldWithIcon, inputType: InputType) =
            view.setInputType(inputType)

        @BindingAdapter("icon")
        @JvmStatic
        fun setIcon(view: LayoutTextFieldWithIcon, icon: Drawable?) = view.setIcon(icon)

        @BindingAdapter("endIconDrawable")
        @JvmStatic
        fun setEndIconDrawable(view: LayoutTextFieldWithIcon, icon: Drawable) =
            view.setEndIconDrawable(icon)

        @BindingAdapter("setEndIconOnClickListener")
        @JvmStatic
        fun setEndIconOnClickListener(
            view: LayoutTextFieldWithIcon,
            listener: OnClickListener,
        ) = view.setEndIconOnClickListener(listener)
    }
}
