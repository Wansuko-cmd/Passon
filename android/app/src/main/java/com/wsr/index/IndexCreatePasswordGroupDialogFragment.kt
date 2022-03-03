package com.wsr.index

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.wsr.R
import com.wsr.databinding.DialogIndexCreatePasswordGroupBinding

class IndexCreatePasswordGroupDialogFragment(
    private val onSubmit: (title: String) -> Unit,
    private val onCancel: () -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity()).apply {
            val inflater = requireActivity().layoutInflater
            val binding = DialogIndexCreatePasswordGroupBinding.inflate(inflater, null, true)
            setView(binding.root)
        }.create()
    }
}