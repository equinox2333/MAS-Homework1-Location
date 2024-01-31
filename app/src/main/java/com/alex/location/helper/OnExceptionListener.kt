package com.alex.location.helper

/**
 * 异常回调
 */
interface OnExceptionListener {

    /**
     * Callback this method on exception
     * [errorCode] -> [LocationErrorCode]
     * [e] -> [Exception]
     */
    fun onException(@LocationErrorCode errorCode: Int, e: Exception)
}