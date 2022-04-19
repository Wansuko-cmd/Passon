package com.wsr.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.wsr.R
import com.wsr.databinding.FragmentLoginBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.AfterTextChanged
import com.wsr.utils.consume
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        val binding = FragmentLoginBinding.bind(view).apply {
            afterTextChanged = com.wsr.AfterTextChanged(loginViewModel::updateEnteredPassword)
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

    private fun showBiometricAuthenticationDialog(
        success: () -> Unit,
        failure: () -> Unit,
    ) {
        val executor = ContextCompat.getMainExecutor(requireContext())
        val biometricPrompt =
            BiometricPrompt(
                this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        when (errorCode) {
                            BiometricPrompt.ERROR_USER_CANCELED,
                            BiometricPrompt.ERROR_CANCELED,
                            BiometricPrompt.ERROR_NEGATIVE_BUTTON,
                            -> {
                            }
                            else -> showMessage("Error $errorCode")
                        }
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        success()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        failure()
                    }
                }
            )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.login_biometric_title))
            .setNegativeButtonText(getString(R.string.login_biometric_negative))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun showMessage(message: String) = Toast
        .makeText(requireContext(), message, Toast.LENGTH_SHORT)
        .show()

    private fun navigateToIndex(userId: String) {
        val action = LoginFragmentDirections.actionLoginFragmentToIndexFragment(userId)
        findNavController().navigate(action)
    }

    private fun navigateToSignUp() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        findNavController().navigate(action)
    }
}
