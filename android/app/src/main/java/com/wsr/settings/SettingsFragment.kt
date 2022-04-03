package com.wsr.settings

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.wsr.R
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
                        success = { Toast.makeText(requireContext(), it.displayName, Toast.LENGTH_SHORT).show() },
                        failure = { Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show() }
                    )
                }
                true
            }
        }

        findPreference<Preference>("login_password")?.apply {

        }

        findPreference<Preference>("logout")?.apply {

        }

        findPreference<Preference>("delete")?.apply {

        }
    }
}
