package com.wsr.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.wsr.R
import com.wsr.databinding.LayoutCheckboxWithTextBinding

class LayoutCheckboxWithText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutCheckboxWithTextBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_checkbox_with_text,
            this,
            true
        )
    }

    val isChecked = binding.layoutCheckboxWithTextCheckbox.isChecked
    var text get() = binding.layoutCheckboxWithTextText.text
        set(value) {
            binding.layoutCheckboxWithTextText.text = value
        }

    private fun setText(text: String) {
        binding.layoutCheckboxWithTextText.text = text
    }

    private fun setChecked(checked: Boolean) {
        binding.layoutCheckboxWithTextCheckbox.isChecked = checked
    }

    private fun setOnClick(onClickListener: OnClickListener) {
        binding.layoutCheckboxWithTextCheckbox.setOnClickListener(onClickListener)
        binding.layoutCheckboxWithTextText.setOnClickListener(onClickListener)
    }

    companion object {

        @BindingAdapter("text")
        @JvmStatic
        fun setText(view: LayoutCheckboxWithText, text: String) = view.setText(text)

        @BindingAdapter("checked")
        @JvmStatic
        fun setChecked(view: LayoutCheckboxWithText, checked: Boolean) = view.setChecked(checked)

        @BindingAdapter("onClick")
        @JvmStatic
        fun setOnClick(view: LayoutCheckboxWithText, onClickListener: OnClickListener) =
            view.setOnClick(onClickListener)
    }
}
