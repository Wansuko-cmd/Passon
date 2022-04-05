package com.wsr.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment

class PassonDialog : DialogFragment() {

    class Builder(private val context: Context) {
        private val inflater = LayoutInflater.from(context)

        fun build(): PassonDialog = PassonDialog()
    }
}
