package com.wsr.index

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.wsr.R

class IndexCreatePasswordGroupDialogFragment(
    private val onSubmit: (title: String) -> Unit,
    private val onCancel: () -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//        val editText = EditText(context).apply {
//            hint = getString(R.string.index_create_password_group_dialog_hint)
//        }

        return AlertDialog.Builder(requireActivity()).apply {
//            setTitle(R.string.index_create_password_group_dialog_title)
            setView(R.layout.dialog_index_create_password_group)
//            setPositiveButton(R.string.index_create_password_group_dialog_positive_button) { _, _ ->
//                onSubmit(
//                    editText.text.toString()
//                )
//            }
//            setNegativeButton(R.string.index_create_password_group_dialog_negative_button) { _, _ -> onCancel() }
        }.create()
    }
}