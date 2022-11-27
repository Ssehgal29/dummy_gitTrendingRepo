package com.dummy.trendinggitrepos.ui.landing

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dummy.trendinggitrepos.R
import com.dummy.trendinggitrepos.base.BaseFragment
import com.dummy.trendinggitrepos.databinding.FragmentLandingBinding
import com.dummy.trendinggitrepos.network.retrofit.ApiService
import com.dummy.trendinggitrepos.utility.Utils.toast

class LandingFragment :
    BaseFragment<LandingViewModel, FragmentLandingBinding, LandingRepository>() {

    private lateinit var navController: NavController
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.apply {
            val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        finish()
                        return
                    }

                    doubleBackToExitPressedOnce = true
                    toast(getString(R.string.press_back_again))

                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackToExitPressedOnce = false
                    }, 2000)

                }
            }
            onBackPressedDispatcher.addCallback(this, callback)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false)
    }

    override fun getViewModel(): Class<LandingViewModel> = LandingViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentLandingBinding = FragmentLandingBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): LandingRepository =
        LandingRepository(retrofitClient.buildApi(ApiService::class.java))

}