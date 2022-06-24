package com.wsr.utils.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.launchInLifecycleScope(
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit,
) = lifecycleScope.launch { viewLifecycleOwner.repeatOnLifecycle(state, block) }

fun Fragment.showDialogIfNotDrawn(tag: String?, builder: () -> DialogFragment) {
    if (isNotDrawnDialogWithThisTag(tag)) builder().showNow(
        childFragmentManager,
        tag
    )
}

private fun Fragment.isNotDrawnDialogWithThisTag(tag: String?) =
    (requireActivity().supportFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dialog == null

fun Fragment.copyToClipboard(text: String) {
    val clip = ClipData.newPlainText("", text)
    val clipBoardManager =
        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipBoardManager.setPrimaryClip(clip)
}

fun Fragment.showMessage(message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT,
    ).show()
}

fun Fragment.showErrorMessage(message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG,
    ).show()
}
