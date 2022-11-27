package com.dummy.trendinggitrepos.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

object Utils {

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