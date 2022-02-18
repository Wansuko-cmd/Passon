package com.wsr.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.wsr.R
import com.wsr.databinding.LayoutTextFieldWithIconBinding

class LayoutTextFieldWithIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTextFieldWithIconBinding

    private val textInputLayout: TextInputLayout
    private val textInputEditText: TextInputEditText
    private val iconView: ImageView

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_text_field_with_icon,
            this,
            true
        )

        textInputLayout = binding.layoutTextFieldWithIconText
        textInputEditText = binding.layoutTextFieldWithIconTextInput
        iconView = binding.layoutTextFieldWithIconIcon
    }


    fun setText(text: String) = textInputEditText.setText(text)

    @JvmName("setEndIconOnClickListener1")
    fun setEndIconOnClickListener(listener: OnClickListener) =
        textInputLayout.setEndIconOnClickListener(listener)

    companion object {

        @BindingAdapter("text")
        @JvmStatic
        fun setText(view: LayoutTextFieldWithIcon, text: String) = view.setText(text)

        private var oldTextValue: String = ""

        @BindingAdapter("afterTextChanged")
        @JvmStatic
        fun setOnTextChanged(view: LayoutTextFieldWithIcon, afterTextChanged: AfterTextChanged) =
            view.textInputEditText.addTextChangedListener {
                if (oldTextValue != it.toString()) {
                    oldTextValue = it.toString()
                    afterTextChanged.block(oldTextValue)
                }
            }

        @BindingAdapter("enabled")
        @JvmStatic
        fun setEnabled(view: LayoutTextFieldWithIcon, enabled: Boolean) {
            view.isEnabled = enabled
            view.textInputEditText.isEnabled = enabled
        }

        @BindingAdapter("inputType")
        @JvmStatic
        fun setInputType(view: LayoutTextFieldWithIcon, inputType: InputType) {
            view.textInputEditText.inputType = inputType.value
        }

        @BindingAdapter("icon")
        @JvmStatic
        fun setIcon(view: LayoutTextFieldWithIcon, icon: Drawable) {
            view.iconView.setImageDrawable(icon)
        }

        @BindingAdapter("endIconDrawable")
        @JvmStatic
        fun setEndIconDrawable(view: LayoutTextFieldWithIcon, icon: Drawable) {
            view.textInputLayout.endIconDrawable = icon
        }

        @BindingAdapter("setEndIconOnClickListener")
        @JvmStatic
        fun setEndIconOnClickListener(
            view: LayoutTextFieldWithIcon,
            listener: OnClickListener
        ) = view.setEndIconOnClickListener(listener)

    }
}

data class AfterTextChanged(val block: (String) -> Unit)
