package com.samuel.embriotechnicaltest.presentation.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import com.samuel.embriotechnicaltest.business.domain.util.*

private val TAG: String = "AppDebug"

fun processQueue(
    context: Context?,
    queue: Queue<StateMessage>,
    stateMessageCallback: StateMessageCallback
) {
    context?.let { ctx ->
        if(!queue.isEmpty()){
            queue.peek()?.let { stateMessage ->
                ctx.onResponseReceived(
                    response = stateMessage.response,
                    stateMessageCallback = stateMessageCallback
                )
            }
        }
    }
}

private fun Context.onResponseReceived(
    response: Response,
    stateMessageCallback: StateMessageCallback
) {
    when(response.uiComponentType){

        is UIComponentType.Toast -> {
            response.message?.let {
                displayToast(
                    message = it,
                    stateMessageCallback = stateMessageCallback
                )
            }
        }

        is UIComponentType.Dialog -> {
            displayDialog(
                response = response,
                stateMessageCallback = stateMessageCallback
            )
        }

        is UIComponentType.None -> {
            response.message?.let {
                silentMessage(
                    message = it,
                    stateMessageCallback = stateMessageCallback
                )
            }
        }
    }
}


private fun Context.displayDialog(
    response: Response,
    stateMessageCallback: StateMessageCallback
){
    response.message?.let { message ->

        when (response.messageType) {

            is MessageType.Error -> {
                displayToast(
                    message = message,
                    stateMessageCallback = stateMessageCallback
                )
            }

            is MessageType.Success -> {
                displayToast(
                    message = message,
                    stateMessageCallback = stateMessageCallback
                )
            }

            is MessageType.Info -> {
                displayToast(
                    message = message,
                    stateMessageCallback = stateMessageCallback
                )
            }

            else -> {
                // do nothing
                stateMessageCallback.removeMessageFromStack()
                null
            }
        }
    }?: stateMessageCallback.removeMessageFromStack()
}

fun Context.displayToast(
    @StringRes message:Int,
    stateMessageCallback: StateMessageCallback
){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    stateMessageCallback.removeMessageFromStack()
}

fun Context.displayToast(
    message:String,
    stateMessageCallback: StateMessageCallback
){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    stateMessageCallback.removeMessageFromStack()
}

fun silentMessage(
    message:String,
    stateMessageCallback: StateMessageCallback
){
    Log.d(TAG, "silentMessage: $message")
    stateMessageCallback.removeMessageFromStack()
}







