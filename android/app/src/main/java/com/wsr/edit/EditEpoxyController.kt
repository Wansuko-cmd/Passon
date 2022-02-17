package com.wsr.edit

import com.airbnb.epoxy.Typed2EpoxyController
import com.wsr.editPasswordRow
import com.wsr.editTitleRow
import com.wsr.layout.AfterTextChanged

class EditEpoxyController(
    private val afterTitleChanged: (newTitle: String) -> Unit,
    private val afterNameChanged: (passwordId: String, newName: String) -> Unit,
    private val afterPasswordChanged: (passwordId: String, newPassword: String) -> Unit,
) : MyTyped2EpoxyController<String, List<PasswordEditUiState>>() {

    override fun buildModels(title: String, list: List<PasswordEditUiState>) {

        editTitleRow {
            id("KEY")
            title(title)
            afterTitleChanged(
                AfterTextChanged(this@EditEpoxyController.afterTitleChanged)
            )
        }

        list.forEach { password ->
            editPasswordRow {
                id(password.id)
                name(password.name)
                password(password.password)
                afterNameChanged(
                    AfterTextChanged(
                        curry(this@EditEpoxyController.afterNameChanged)(password.id)
                    )
                )
                afterPasswordChanged(
                    AfterTextChanged(
                        curry(this@EditEpoxyController.afterPasswordChanged)(password.id)
                    )
                )
            }
        }
    }
}

fun <T, U, V> curry(f: (T, U) -> V): (T) -> (U) -> V = { t -> { u -> f(t, u) } }

abstract class MyTyped2EpoxyController<T, U> : Typed2EpoxyController<T, U>() {
    private var data1: T? = null
    private var data2: U? = null

    fun initializeFirstData(init: T) {
        if(data1 == null) setFirstData(init)
    }

    fun initializeSecondData(init: U) {
        if(data2 == null) setSecondData(init)
    }

    private fun setFirstData(newData: T) {
        data1 = newData
        if (data2 != null) setData(data1, data2)
    }

    private fun setSecondData(newData: U) {
        data2 = newData
        if (data1 != null) setData(data1, data2)
    }
}
