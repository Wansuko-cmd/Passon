package com.wsr.utils

import com.airbnb.epoxy.Typed2EpoxyController

abstract class MyTyped2EpoxyController<T, U> : Typed2EpoxyController<T, U>() {
    private var data1: T? = null
    private var data2: U? = null

    fun initializeFirstData(init: T) {
        if (data1 == null) setFirstData(init)
    }

    fun initializeSecondData(init: U) {
        if (data2 == null) setSecondData(init)
    }

    fun refresh(newData1: T? = null, newData2: U? = null) {
        newData1?.let { this.data1 = it }
        newData2?.let { this.data2 = it }
        if (this.data1 != null && this.data2 != null) setData(this.data1, this.data2)
    }

    fun setFirstData(newData: T) {
        data1 = newData
        if (data2 != null) setData(data1, data2)
    }

    fun setSecondData(newData: U) {
        data2 = newData
        if (data1 != null) setData(data1, data2)
    }
}