package com.wsr.index

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class IndexCreatePasswordGroupDialogFragment(
    private val onPositive: (title: String) -> Unit,
    private val onNegative: () -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val editText = EditText(context).apply {
            hint = "タイトル"
        }

        return activity?.let {
            AlertDialog.Builder(it).apply {
                setTitle("新規作成")
                setMessage("タイトル")
                setView(editText)
                setPositiveButton("作成") { _, _ -> onPositive(editText.text.toString()) }
                setNegativeButton("キャンセル") { _, _ -> onNegative() }

            }.create()
        } ?: throw Exception()
    }
}