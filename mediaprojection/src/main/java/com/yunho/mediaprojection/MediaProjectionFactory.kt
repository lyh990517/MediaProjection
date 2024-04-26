package com.yunho.mediaprojection

object MediaProjectionFactory {
    fun create() : MediaProjectionRequester = MediaProjectionRequesterImpl()
}