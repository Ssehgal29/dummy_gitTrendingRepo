package com.dummy.trendinggitrepos.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.dummy.trendinggitrepos.R
import com.dummy.trendinggitrepos.databinding.ActivitySplashBinding
import com.dummy.trendinggitrepos.utility.Utils.startNewActivity

class SplashActivity : AppCompatActivity() {

    private var mBinder: ActivitySplashBinding? = null
    private val binding get() = mBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startNewActivity(MainActivity::class.java)
        }, 500)

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinder = null
    }
}