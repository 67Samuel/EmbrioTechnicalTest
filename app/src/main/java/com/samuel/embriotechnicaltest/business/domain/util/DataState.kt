package com.samuel.embriotechnicaltest.business.domain.util

data class DataState<T>(
    val stateMessage: StateMessage? = null,
    val data: T? = null,
) {

    companion object {

        fun <T> error(
            response: Response,
        ): DataState<T> {
            return DataState(
                stateMessage = StateMessage(
                    response
                ),
                data = null,
            )
        }

        fun <T> data(
            response: Response?,
            data: T? = null,
        ): DataState<T> {
            return DataState(
                stateMessage = response?.let {
                    StateMessage(
                        it
                    )
                },
                data = data,
            )
        }

    }

    override fun toString(): String {
        return "DataState(stateMessage=$stateMessage, data=$data)"
    }
}
