package com.dummy.trendinggitrepos.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.opengl.Visibility
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.dummy.trendinggitrepos.R
import com.dummy.trendinggitrepos.network.ProcessDialog
import com.dummy.trendinggitrepos.network.Resource
import com.google.android.material.snackbar.Snackbar

object Utils {

    fun String?.orDefaultString(defaultValue: String = ""): String {
        this?.let { str ->
            return str
        } ?: kotlin.run {
            return defaultValue
        }
    }

    infix fun View.click(click: () -> Unit) {
        setOnClickListener { click() }
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.enable(enabled: Boolean) {
        isEnabled = enabled
        alpha = if (enabled) 1f else 0.5f
    }

    fun View.isVisible(): Boolean {
        return visibility == View.VISIBLE
    }

    fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
        Intent(this, activity).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(it)
            finish()
        }
    }

    fun Activity.toast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Context.toast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.toast(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun View.snackBar(message: String/* action: (() -> Unit)? = null*/) {
        val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snackBar.setAction("Ok") {
            snackBar.dismiss()
        }
        /*action?.let {
            snackBar.setAction("Ok") {
                snackBar.dismiss()
            }
        }*/

        snackBar.show()
    }

    fun Fragment.showDialog(isDialog: Boolean) {
        if (isDialog) {
            ProcessDialog.startDialog(requireContext())
        }
    }

    fun Fragment.dismissDialog(isDialog: Boolean) {
        if (isDialog) {
            ProcessDialog.dismissDialog()
        }
    }

    fun Fragment.handleApiError(
        failure: Resource.Failure/*,
    ok : (() -> Unit)? = null*/
    ) {
        when {
            failure.isOtherError -> {
                Log.i(
                    "TAG",
                    "handleApiError: ${failure.errorBody} ${failure.errorMessage} ${failure.errorCode}"
                )
                requireView().snackBar(getString(R.string.something_wrong)/*,ok*/)
            }
            else -> requireView().snackBar("${failure.errorMessage} ${failure.errorCode}")
        }
    }

    fun RecyclerView.disableItemAnimator() {
        (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }

    fun isNetworkAvailable(mContext: Context): Boolean {
        /* getting systems Service connectivity manager */
        val mConnectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        @SuppressLint("MissingPermission") val mNetworkInfos = mConnectivityManager.allNetworkInfo
        for (mNetworkInfo in mNetworkInfos) {
            if (mNetworkInfo.state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }

}