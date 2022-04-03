package com.wsr.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.wsr.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}
