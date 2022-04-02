package com.wsr.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.wsr.R
import com.wsr.databinding.FragmentSignUpBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.layout.AfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val signUpViewModel by viewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        FragmentSignUpBinding.bind(view).apply {
            afterDisplayNameChanged = AfterTextChanged(signUpViewModel::updateDisplayName)
            afterLoginPasswordChanged = AfterTextChanged(signUpViewModel::updateLoginPassword)
            signUpFragmentNextButton.setOnClickListener { signUpViewModel.signUp() }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            signUpViewModel.navigateToIndexEvent.collect { navigateToIndex() }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            signUpViewModel.succeedCreatingUserEvent.collect { success ->
                if (success) showMessage(getString(R.string.sign_up_success_message))
                else showMessage(getString(R.string.sign_up_failure_message))
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToIndex() {
        val action = SignUpFragmentDirections.actionSignUpFragmentToIndexFragment()
        findNavController().navigate(action)
    }
}
