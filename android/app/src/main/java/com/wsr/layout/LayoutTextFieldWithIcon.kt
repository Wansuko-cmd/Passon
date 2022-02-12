package com.wsr.layout

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
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
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutTextFieldWithIconBinding

    private val textInputLayout: TextInputLayout
    private val textInputEditText: TextInputEditText
    private val iconView: ImageView

    fun setText(text: String) = textInputEditText.setText(text)

    @JvmName("setEndIconOnClickListener1")
    fun setEndIconOnClickListener(listener: OnClickListener) =
        textInputLayout.setEndIconOnClickListener(listener)


    init {
        val inflater = LayoutInflater.from(context)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.layout_text_field_with_icon, this, true)

        textInputLayout = binding.layoutTextFieldWithIconText
        textInputEditText = binding.layoutTextFieldWithIconTextInput
        iconView = binding.layoutTextFieldWithIconIcon

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LayoutTextFieldWithIcon,
            0, 0
        ).apply {
            setTextInputLayout()
            setTextInputEditText()
            setIconView()
        }
    }

    private fun TypedArray.setTextInputLayout() {
        val endIcon = getResourceId(R.styleable.LayoutTextFieldWithIcon_endIconDrawable, NULL)
        if (endIcon != NULL) {
            textInputLayout.setEndIconDrawable(endIcon)
        }
    }

    private fun TypedArray.setTextInputEditText() {
        val text = getString(R.styleable.LayoutTextFieldWithIcon_text)
        textInputEditText.setText(text)

        val isEnabled = getBoolean(R.styleable.LayoutTextFieldWithIcon_enabled, true)
        textInputEditText.isEnabled = isEnabled
    }

    private fun TypedArray.setIconView() {
        val icon = getResourceId(R.styleable.LayoutTextFieldWithIcon_icon, NULL)
        if (icon != NULL) iconView.setImageResource(icon)
    }

    companion object {
        private const val NULL = -1

        @BindingAdapter("text")
        @JvmStatic
        fun setText(view: LayoutTextFieldWithIcon, text: String) = view.setText(text)

        @BindingAdapter("setEndIconOnClickListener")
        @JvmStatic
        fun setEndIconOnClickListener(view: LayoutTextFieldWithIcon, listener: OnClickListener) =
            view.setEndIconOnClickListener(listener)
    }
}
