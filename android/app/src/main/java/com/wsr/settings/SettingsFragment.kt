package com.wsr.settings

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceFragmentCompat
import com.wsr.R

class SettingsFragment : PreferenceFragmentCompat() {

    private val args: SettingsFragmentArgs by navArgs()
    private val userId by lazy { args.userId }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
