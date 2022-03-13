package com.wsr.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.wsr.R
import com.wsr.databinding.FragmentLoginBinding
import com.wsr.ext.launchInLifecycleScope
import com.wsr.layout.AfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var _binding: FragmentLoginBinding
    private val binding get() = _binding

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding.afterTextChanged = AfterTextChanged(loginViewModel::updateEnteredPassword)

        binding.loginFragmentNextButton.setOnClickListener { loginViewModel.checkPassword() }

        launchInLifecycleScope(Lifecycle.State.STARTED) {
            loginViewModel.checkPasswordEvent.collect {
                if(it.passed) {
                    showMessage(getString(R.string.login_biometric_success_message))
                    navigateToIndex()
                } else showMessage("間違えています input=${binding.loginFragmentLoginText.text}")
            }
        }

        // 生体認証
        when (BiometricManager.from(requireContext()).canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                binding.loginFragmentFingerPrintButton.setOnClickListener {
                    biometricAuthentication(
                        success = {
                            showMessage(getString(R.string.login_biometric_success_message))
                            navigateToIndex()
                        },
                        failure = { showMessage(getString(R.string.login_biometric_failure_message)) }
                    )
                }
            }
            else -> binding.loginFragmentFingerPrintButton.visibility = View.GONE
        }
    }

    private fun biometricAuthentication(
        success: () -> Unit,
        failure: () -> Unit,
    ) {
        val executor = ContextCompat.getMainExecutor(requireContext())
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                success()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                failure()
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.login_biometric_title))
            .setNegativeButtonText(getString(R.string.login_biometric_negative))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun showMessage(message: String) = Toast
        .makeText(requireContext(), message, Toast.LENGTH_LONG)
        .show()

    private fun navigateToIndex() {
        findNavController().navigate(R.id.index_fragment)
    }
}