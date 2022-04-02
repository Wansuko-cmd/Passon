package com.wsr.index.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import com.wsr.databinding.DialogIndexCreatePasswordGroupBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.ext.sharedViewModel
import com.wsr.index.IndexViewModel
import com.wsr.utils.autoCleared
import org.koin.androidx.viewmodel.ViewModelOwner.Companion.from
import org.koin.androidx.viewmodel.ext.android.viewModel

class IndexCreatePasswordGroupDialogFragment : DialogFragment() {

    private val indexCreatePasswordGroupDialogViewModel by viewModel<IndexCreatePasswordGroupDialogViewModel>()
    private var binding: DialogIndexCreatePasswordGroupBinding by autoCleared()
    private val indexViewModel by sharedViewModel<IndexViewModel>(owner = {
        from(requireParentFragment())
    })

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val userId = arguments?.getString("userId")
            ?: throw NoSuchElementException("userId does not exist in bundle at IndexCreatePasswordGroupDialogFragment")

        binding =
            DialogIndexCreatePasswordGroupBinding.inflate(requireActivity().layoutInflater).apply {
                dialogIndexCreatePasswordGroupSubmitButton.setOnClickListener {
                    indexViewModel.createPasswordGroup(
                        userId,
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
        fun create(userId: String): IndexCreatePasswordGroupDialogFragment {
            return IndexCreatePasswordGroupDialogFragment().apply {
                val bundle = Bundle()
                bundle.putString("userId", userId)
                arguments = bundle
            }
        }
    }
}
