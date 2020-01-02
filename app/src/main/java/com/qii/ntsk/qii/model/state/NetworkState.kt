package com.qii.ntsk.qii.model.state

data class NetworkState(
        val status: Status,
        val responseCode: Int? = null
)

enum class Status {
    LOADING,
    PAGING,
    SUCCESS,
    FAILED
}