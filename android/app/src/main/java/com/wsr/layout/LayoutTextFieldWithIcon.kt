package com.wsr.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.wsr.R
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

    companion object {

        @BindingAdapter("text")
        @JvmStatic
        fun setText(view: LayoutTextFieldWithIcon, text: String) =
            view.binding.layoutTextFieldWithIconTextInput.setText(text)

        @BindingAdapter("hint")
        @JvmStatic
        fun setHint(view: LayoutTextFieldWithIcon, hint: String) {
            view.binding.layoutTextFieldWithIconTextInput.hint = hint
        }

        @BindingAdapter("maxLines")
        @JvmStatic
        fun setMaxLines(view: LayoutTextFieldWithIcon, maxLines: Int) {
            view.binding.layoutTextFieldWithIconTextInput.maxLines = maxLines
        }

        @BindingAdapter("afterTextChanged")
        @JvmStatic
        fun setOnTextChanged(view: LayoutTextFieldWithIcon, afterTextChanged: AfterTextChanged) =
            view.binding.layoutTextFieldWithIconTextInput.addTextChangedListener {
                afterTextChanged.block(
                    it.toString()
                )
            }

        @BindingAdapter("enabled")
        @JvmStatic
        fun setEnabled(view: LayoutTextFieldWithIcon, enabled: Boolean) {
            view.isEnabled = enabled
            view.binding.layoutTextFieldWithIconTextInput.isEnabled = enabled
        }

        @BindingAdapter("inputType")
        @JvmStatic
        fun setInputType(view: LayoutTextFieldWithIcon, inputType: InputType) {
            view.binding.layoutTextFieldWithIconTextInput.inputType = inputType.value
        }

        @BindingAdapter("icon")
        @JvmStatic
        fun setIcon(view: LayoutTextFieldWithIcon, icon: Drawable?) {
            view.binding.layoutTextFieldWithIconIcon.setImageDrawable(icon)
        }

        @BindingAdapter("endIconDrawable")
        @JvmStatic
        fun setEndIconDrawable(view: LayoutTextFieldWithIcon, icon: Drawable) {
            view.binding.layoutTextFieldWithIconText.endIconDrawable = icon
        }

        @BindingAdapter("setEndIconOnClickListener")
        @JvmStatic
        fun setEndIconOnClickListener(
            view: LayoutTextFieldWithIcon,
            listener: OnClickListener
        ) = view.binding.layoutTextFieldWithIconText.setEndIconOnClickListener(listener)
    }
}


