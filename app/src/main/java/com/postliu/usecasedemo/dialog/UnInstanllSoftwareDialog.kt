package com.postliu.usecasedemo.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.dylanc.viewbinding.binding
import com.postliu.usecasedemo.R
import com.postliu.usecasedemo.databinding.DialogLaunchSoftwareLayoutBinding
import com.postliu.usecasedemo.util.dpToPx

/**
 * 卸载软件提示窗
 *
 * @constructor
 *
 * @param context
 */
class UnInstallSoftwareDialog private constructor(context: Context) : AlertDialog(context),
    DialogBaseInterface<UnInstallSoftwareDialog> {

    companion object {
        fun build(context: Context) = UnInstallSoftwareDialog(context)
    }

    private val binding: DialogLaunchSoftwareLayoutBinding by binding()

    private var logo: Drawable? = null

    private var content: String = ""

    private var cancelListener: CancelListener = {}

    private var sureListener: SureListener = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            setLayout(
                context.dpToPx(350f).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        with(binding) {
            dialogTitle.text = "卸载应用"
            dialogTitle.setCompoundDrawables(
                null,
                logo?.apply {
                    setBounds(0, 0, minimumWidth, minimumHeight)
                } ?: ResourcesCompat.getDrawable(context.resources, R.mipmap.ic_launcher, null),
                null,
                null
            )
            dialogMessage.text = content
            dialogBtnCancel.setOnClickListener { cancelListener(this@UnInstallSoftwareDialog) }
            dialogBtnSure.setOnClickListener { sureListener(this@UnInstallSoftwareDialog) }
        }
    }

    fun setLogo(icon: Drawable?): UnInstallSoftwareDialog {
        this.logo = icon
        return this
    }

    override fun setMessage(message: String): UnInstallSoftwareDialog {
        this.content = message
        return this
    }

    override fun setCancelClickListener(cancelListener: CancelListener): UnInstallSoftwareDialog {
        this.cancelListener = cancelListener
        return this
    }

    override fun setSureClickListener(sureListener: SureListener): UnInstallSoftwareDialog {
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