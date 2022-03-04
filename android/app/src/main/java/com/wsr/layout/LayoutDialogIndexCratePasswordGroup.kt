package com.wsr.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.wsr.R
import com.wsr.databinding.LayoutDialogIndexCreatePasswordGroupBinding

class LayoutDialogIndexCratePasswordGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutDialogIndexCreatePasswordGroupBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_dialog_index_create_password_group,
            this,
            true
        )
    }

    private fun setOnTextChanged(afterTextChanged: AfterTextChanged) {
        binding.layoutDialogIndexCreatePasswordGroupEditText
            .addTextChangedListener { afterTextChanged.block(it.toString()) }
    }

    private fun setOnSubmitButton(listener: OnClickListener) {
        binding.layoutDialogIndexCreatePasswordGroupSubmitButton.setOnClickListener(listener)
    }

    private fun setOnCancelButton(listener: OnClickListener) {
        binding.layoutDialogIndexCreatePasswordGroupCancelButton.setOnClickListener(listener)
    }

    private fun setOnCheckbox(onClickListener: OnClickListener) {
        LayoutCheckboxWithText.setOnClick(
            binding.layoutDialogIndexCreatePasswordGroupCheckBox,
            onClickListener
        )
    }

    private fun setChecked(checked: Boolean) {
        LayoutCheckboxWithText.setChecked(
            binding.layoutDialogIndexCreatePasswordGroupCheckBox,
            checked
        )
    }

    companion object {

        @BindingAdapter("afterTextChanged")
        @JvmStatic
        fun setOnTextChanged(
            view: LayoutDialogIndexCratePasswordGroup,
            afterTextChanged: AfterTextChanged
        ) =
            view.setOnTextChanged(afterTextChanged)

        @BindingAdapter("onSubmitButton")
        @JvmStatic
        fun setOnSubmitButton(
            view: LayoutDialogIndexCratePasswordGroup,
            listener: OnClickListener
        ) =
            view.setOnSubmitButton(listener)

        @BindingAdapter("onCancelButton")
        @JvmStatic
        fun setOnCancelButton(
            view: LayoutDialogIndexCratePasswordGroup,
            listener: OnClickListener
        ) =
            view.setOnCancelButton(listener)

        @BindingAdapter("onCheckbox")
        @JvmStatic
        fun setOnCheckbox(view: LayoutDialogIndexCratePasswordGroup, listener: OnClickListener) =
            view.setOnCheckbox(listener)

        @BindingAdapter("checked")
        @JvmStatic
        fun setChecked(view: LayoutDialogIndexCratePasswordGroup, checked: Boolean) =
            view.setChecked(checked)
    }
}