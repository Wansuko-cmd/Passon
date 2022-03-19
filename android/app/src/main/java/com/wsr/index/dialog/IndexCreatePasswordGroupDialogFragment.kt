package com.wsr.index.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import com.wsr.databinding.DialogIndexCreatePasswordGroupBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.BundleHandler.Companion.getValue
import com.wsr.index.dialog.BundleHandler.Companion.putValue
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class IndexCreatePasswordGroupDialogFragment : DialogFragment() {

    private val indexCreatePasswordGroupDialogViewModel by viewModel<IndexCreatePasswordGroupDialogViewModel>()
    private lateinit var binding: DialogIndexCreatePasswordGroupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val indexViewModel = arguments?.getValue<IndexViewModel>("indexViewModel")
        val email = arguments?.getString("email") ?: ""

        binding =
            DialogIndexCreatePasswordGroupBinding.inflate(requireActivity().layoutInflater).apply {
                dialogIndexCreatePasswordGroupSubmitButton.setOnClickListener {
                    indexViewModel?.createPasswordGroup(
                        email,
                        dialogIndexCreatePasswordGroupEditText.text.toString(),
                        indexCreatePasswordGroupDialogViewModel.shouldNavigateToEdit.value,
                    )
                    dismiss()
                }
                dialogIndexCreatePasswordGroupCancelButton.setOnClickListener {
                    dismiss()
                }
                onClickCheckbox = View.OnClickListener {
                    indexCreatePasswordGroupDialogViewModel.switchChecked()
                }
            }

        return AlertDialog.Builder(requireActivity()).apply { setView(binding.root) }.create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            indexCreatePasswordGroupDialogViewModel.shouldNavigateToEdit.collect {
                binding.checked = it
            }
        }
    }

    companion object {
        fun create(
            indexViewModel: IndexViewModel,
            email: String,
        ): IndexCreatePasswordGroupDialogFragment {
            return IndexCreatePasswordGroupDialogFragment().apply {
                val bundle = Bundle()
                bundle.putValue("indexViewModel", indexViewModel)
                bundle.putString("email", email)
                arguments = bundle
            }
        }
    }
}

private class BundleHandler<T : Any> private constructor(val value: T) : Serializable {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T : Any> Bundle.getValue(key: String): T? = (this.getSerializable(key) as? BundleHandler<T>)?.value
        fun <T : Any> Bundle.putValue(key: String, value: T) = this.putSerializable(key, BundleHandler(value))
    }
}
