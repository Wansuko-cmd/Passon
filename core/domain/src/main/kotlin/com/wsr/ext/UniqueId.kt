package com.wsr.ext

import java.util.*

data class UniqueId(val value: String = UUID.randomUUID().toString())
