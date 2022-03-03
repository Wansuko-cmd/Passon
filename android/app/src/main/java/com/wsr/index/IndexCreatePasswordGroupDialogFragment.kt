package com.wsr.index

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.wsr.databinding.DialogIndexCreatePasswordGroupBinding
import java.io.Serializable

class IndexCreatePasswordGroupDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogIndexCreatePasswordGroupBinding.inflate(requireActivity().layoutInflater)
        return AlertDialog.Builder(requireActivity()).apply{ setView(binding.root) }.create()
    }

    companion object {
        fun create(
            onSubmit: (title: String) -> Unit,
            onCancel: () -> Unit,
        ): IndexCreatePasswordGroupDialogFragment {
            return IndexCreatePasswordGroupDialogFragment().apply {
                val bundle = Bundle()
                bundle.putSerializable(OnSubmit.key, OnSubmit(onSubmit))
                bundle.putSerializable(OnCancel.key, OnCancel(onCancel))
                arguments = bundle
            }
        }
    }
}

private class OnSubmit(val block: (title: String) -> Unit): Serializable {
    companion object {
        const val key = "onSubmit"
    }
}

private class OnCancel(val block: () -> Unit): Serializable {
    companion object {
        const val key = "onCancel"
    }
}