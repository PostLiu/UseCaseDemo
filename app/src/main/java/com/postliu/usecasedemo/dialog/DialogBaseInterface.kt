package com.postliu.usecasedemo.dialog

interface DialogBaseInterface<D> {

    fun setMessage(message: String): D

    fun setCancelClickListener(cancelListener: CancelListener):D

    fun setSureClickListener(sureListener: SureListener):D
}