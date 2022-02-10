package com.wsr.index

import android.util.Log
import com.airbnb.epoxy.TypedEpoxyController
import com.wsr.indexPasswordGroupRow

class IndexEpoxyController(private val onClickListener: (String) -> Unit) :
    TypedEpoxyController<List<PasswordGroupIndexUiState>>() {

    override fun buildModels(list: List<PasswordGroupIndexUiState>) {
        list.forEach { passwordGroup ->
            indexPasswordGroupRow {
                id(passwordGroup.id)
                title(passwordGroup.title)
                listener { _, _, _, _ ->
                    this@IndexEpoxyController.onClickListener(passwordGroup.id)
                }
            }
        }
    }
}