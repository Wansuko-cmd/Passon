package com.wsr.login

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wsr.R
import com.wsr.utils.ext.showMessage

fun Fragment.showBiometricAuthenticationDialog(
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
