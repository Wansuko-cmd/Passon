package com.wsr.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.wsr.R
import com.wsr.databinding.FragmentLoginBinding
import com.wsr.layout.AfterTextChanged
import com.wsr.utils.consume
import com.wsr.utils.ext.launchInLifecycleScope
import com.wsr.utils.ext.showMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val binding = FragmentLoginBinding.bind(view).apply {
            afterTextChanged = AfterTextChanged(loginViewModel::updateEnteredPassword)
            loginFragmentPassword.onEnterClicked(loginViewModel::checkPassword)
            loginFragmentNextButton.setOnClickListener { loginViewModel.checkPassword() }
            loginFragmentSignUpButton.setOnClickListener { navigateToSignUp() }
        }

        loginViewModel.fetch()

        val loginUserEpoxyController = LoginUserEpoxyController(
            onSelected = loginViewModel::updateIsSelected,
        )

        binding.loginFragmentUserRecyclerView.apply {
            setHasFixedSize(true)
            adapter = loginUserEpoxyController.adapter
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            loginViewModel.uiState.collect { loginUiState ->
                loginUiState.users.consume(
                    success = loginUserEpoxyController::setData,
                    failure = { showMessage(it.message) },
                    loading = { /* do nothing */ }
                )
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            loginViewModel.navigateToIndex.collect { navigateToIndex(it) }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            loginViewModel.shouldPassEvent.collect { success ->
                if (success) showMessage(getString(R.string.login_success_message))
                else showMessage(getString(R.string.login_password_wrong))
            }
        }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            loginViewModel.navigateToSignUp.collect { navigateToSignUp() }
        }

        // 生体認証
        when (BiometricManager.from(requireContext()).canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                binding.loginFragmentFingerPrintButton.setOnClickListener {
                    showBiometricAuthenticationDialog(
                        success = { loginViewModel.passAuthentication() },
                        failure = { showMessage(getString(R.string.login_failure_message)) }
                    )
                }
            }
            else -> binding.loginFragmentFingerPrintButton.visibility = View.GONE
        }
    }

    private fun navigateToIndex(userId: String) {
        val action = LoginFragmentDirections.actionLoginFragmentToIndexFragment(userId)
        findNavController().navigate(action)
    }

    private fun navigateToSignUp() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        findNavController().navigate(action)
    }
}
