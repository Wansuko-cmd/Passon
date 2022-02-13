package com.wsr.layout

enum class InputType(val value: Int) {
    None(0),
    Text(1),
    TextPassword(129);
}

fun String.toInputType() = InputType.values()
    .toList()
    .filter { it.name.lowercase() == this.lowercase() }
    .getOrNull(1) ?: InputType.Text

