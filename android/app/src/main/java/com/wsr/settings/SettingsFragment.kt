package com.wsr.settings

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.wsr.R
import com.wsr.dialog.BundleValue.Companion.getValue
import com.wsr.dialog.PassonDialog
import com.wsr.ext.showDialogIfNotDrawn
import com.wsr.layout.InputType
import com.wsr.maybe.consume
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val args: SettingsFragmentArgs by navArgs()
    private val userId by lazy { args.userId }

    private val settingsViewModel: SettingsViewModel by viewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<Preference>("display_name")?.apply {
            setOnPreferenceClickListener {
                lifecycleScope.launch {
                    settingsViewModel.getDisplayName(userId).consume(
                        success = {
                            showUpdateDisplayNameDialog(it.displayName)
                        },
                        failure = {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
                true
            }
        }

        findPreference<Preference>("login_password")?.apply {
            setOnPreferenceClickListener {
                showUpdateLoginPasswordDialog()
                true
            }
        }

        findPreference<Preference>("logout")?.apply {
            setOnPreferenceClickListener {
                navigateToLogin()
                true
            }
        }

        findPreference<Preference>("delete_user")?.apply {
            setOnPreferenceClickListener {
                showDeleteUserDialog()
                true
            }
        }
    }

    private fun showUpdateDisplayNameDialog(currentDisplayName: String) {
        showDialogIfNotDrawn(tag) {
            PassonDialog.builder()
                .setTitle(getString(R.string.settings_update_display_name_dialog_title))
                .setEditText(
                    key = "displayName",
                    hint = getString(R.string.settings_update_display_name_dialog_hint),
                    text = currentDisplayName
                )
                .setButtons(
                    positiveText = getString(R.string.settings_update_display_name_dialog_positive_button),
                    positive = { bundle ->
                        bundle.getValue<String>("displayName")
                            ?.also { settingsViewModel.updateDisplayName(userId, it) }
                    },
                    negativeText = getString(R.string.settings_update_display_name_dialog_negative_button),
                    negative = {},
                )
                .build()
        }
    }

    private fun showUpdateLoginPasswordDialog() {
        showDialogIfNotDrawn(tag) {
            PassonDialog.builder()
                .setTitle(getString(R.string.settings_update_login_password_dialog_title))
                .setEditText(
                    key = "loginPassword",
                    hint = getString(R.string.settings_update_login_password_dialog_hint),
                    inputType = InputType.TextPassword,
                )
                .setEditText(
                    key = "loginPasswordConfirmation",
                    hint = getString(R.string.settings_update_login_password_dialog_confirmation_hint),
                    inputType = InputType.TextPassword,
                )
                .setButtons(
                    positiveText = getString(R.string.index_create_password_group_dialog_positive_button),
                    positive = { bundle ->
                        val loginPassword = bundle.getValue<String>("loginPassword") ?: return@setButtons
                        val loginPasswordConfirmation = bundle.getValue<String>("loginPasswordConfirmation") ?: return@setButtons
                        settingsViewModel.updateLoginPassword(userId, loginPassword, loginPasswordConfirmation)
                    },
                    negativeText = getString(R.string.settings_update_login_password_dialog_negative_button),
                    negative = {},
                )
                .build()
        }
    }

    private fun showDeleteUserDialog() {
        showDialogIfNotDrawn(tag) {
            PassonDialog.builder()
                .setTitle(getString(R.string.settings_delete_user_dialog_title))
                .setMessage(getString(R.string.settings_delete_user_dialog_message))
                .setDangerButtons(
                    positiveText = getString(R.string.settings_delete_user_dialog_positive_button),
                    positive = { settingsViewModel.deleteUser(userId) },
                    negativeText = getString(R.string.settings_delete_user_dialog_negative_button),
                    negative = {},
                )
                .build()
        }
    }

    private fun navigateToLogin() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}
