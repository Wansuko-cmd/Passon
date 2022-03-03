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

    private lateinit var _binding: DialogIndexCreatePasswordGroupBinding
    private val binding get() = _binding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        _binding = DialogIndexCreatePasswordGroupBinding.inflate(inflater, null, true)
        return AlertDialog.Builder(requireActivity()).apply {
            setView(binding.root)
        }.create()
    }
}