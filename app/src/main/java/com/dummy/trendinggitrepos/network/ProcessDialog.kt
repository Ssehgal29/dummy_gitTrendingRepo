package com.dummy.trendinggitrepos.network

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.dummy.trendinggitrepos.R

object ProcessDialog {
    private var progressDialog: Dialog? = null

    @JvmStatic
    fun startDialog(context: Context) {
        if (!isShowing()) {
            if (!(context as Activity).isFinishing) {
                progressDialog = Dialog(context).apply {
                    setCancelable(false)
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    setContentView(R.layout.process_dialog)
                    show()
                }
            }
        }
    }

    @JvmStatic
    fun dismissDialog() {
        try {
            progressDialog?.let { pd ->
                if (pd.isShowing) pd.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            progressDialog = null
        }
    }

    private fun isShowing(): Boolean {
        progressDialog?.let { pd ->
            return pd.isShowing
        }
        return false
    }
}