package com.fcm.simpleblog.util

data class CmResDto<T>(
    val resultCode: T,
    val resultMsg: String,
    val data: T
)