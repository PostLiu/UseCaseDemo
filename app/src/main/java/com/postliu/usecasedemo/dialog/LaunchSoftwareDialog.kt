package com.postliu.usecasedemo.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AlertDialog
import com.dylanc.viewbinding.binding
import com.postliu.usecasedemo.databinding.DialogLaunchSoftwareLayoutBinding

typealias CancelListener = Dialog.() -> Unit
typealias SureListener = Dialog.() -> Unit

class LaunchSoftwareDialog private constructor(context: Context) : AlertDialog(context) {

    companion object {
        fun build(context: Context) = LaunchSoftwareDialog(context)
    }

    private val binding: DialogLaunchSoftwareLayoutBinding by binding()

    private var content: String = ""

    private var cancelListener: CancelListener = {}

    private var sureListener: SureListener = {}

    private var outOfSideHide: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setGravity(Gravity.BOTTOM)
        }
        setCancelable(outOfSideHide)
        with(binding) {
            dialogMessage.text = content
            dialogBtnCancel.setOnClickListener {
                cancelListener.invoke(this@LaunchSoftwareDialog)
            }
            dialogBtnSure.setOnClickListener {
                sureListener.invoke(this@LaunchSoftwareDialog)
            }
        }
    }

    fun setIsCancelable(cancelable: Boolean): LaunchSoftwareDialog {
        this.outOfSideHide = cancelable
        return this
    }

    fun setMessage(content: String): LaunchSoftwareDialog {
        this.content = content
        return this
    }

    fun setCancelListener(cancelListener: CancelListener): LaunchSoftwareDialog {
        this.cancelListener = cancelListener
        return this
    }

    fun setSureListener(sureListener: SureListener): LaunchSoftwareDialog {
        this.sureListener = sureListener
        return this
    }

    override fun show() {
        if (isShowing) {
            return
        }
        super.show()
    }
}