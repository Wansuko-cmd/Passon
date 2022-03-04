package com.wsr.index

data class IndexRefreshEvent(
    val navigateToEditEvent: NavigateToEditEvent
)

sealed class NavigateToEditEvent {
    class True(val passwordGroupId: String): NavigateToEditEvent()
    object False: NavigateToEditEvent()
}
