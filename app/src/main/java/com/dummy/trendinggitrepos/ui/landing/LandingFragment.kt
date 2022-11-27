package com.dummy.trendinggitrepos.ui.landing

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dummy.trendinggitrepos.R
import com.dummy.trendinggitrepos.base.BaseFragment
import com.dummy.trendinggitrepos.databinding.FragmentLandingBinding
import com.dummy.trendinggitrepos.model.TrendingReposData
import com.dummy.trendinggitrepos.network.Resource
import com.dummy.trendinggitrepos.network.retrofit.ApiService
import com.dummy.trendinggitrepos.utility.Utils.disableItemAnimator
import com.dummy.trendinggitrepos.utility.Utils.dismissDialog
import com.dummy.trendinggitrepos.utility.Utils.enable
import com.dummy.trendinggitrepos.utility.Utils.handleApiError
import com.dummy.trendinggitrepos.utility.Utils.isNetworkAvailable
import com.dummy.trendinggitrepos.utility.Utils.showDialog
import com.dummy.trendinggitrepos.utility.Utils.snackBar
import com.dummy.trendinggitrepos.utility.Utils.toast
import com.google.gson.Gson
import okhttp3.ResponseBody

class LandingFragment :
    BaseFragment<LandingViewModel, FragmentLandingBinding, LandingRepository>() {

    private lateinit var navController: NavController
    private var doubleBackToExitPressedOnce = false
    private var trendingRepoAdapter: TrendingRepoAdapter? = null

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

        context?.let {
            if (isNetworkAvailable(it)) viewModel.getTrendingRepos() else {
                view?.apply {
                    snackBar(getString(R.string.no_internet))
                    binding.searchTrendingRepos.enable(false)
                }
            }
        }

        with(binding.searchTrendingRepos) {
            addTextChangedListener {
                trendingRepoAdapter?.let { tra ->
                    enable(true)
                    tra.filter.filter(it)
                } ?: kotlin.run { enable(false) }
            }
        }

        viewModel.trendingRepos.observe(viewLifecycleOwner) {
            dismissDialog(true)
            when (it) {
                is Resource.Success -> {
                    with(binding) {
                        trendingRepoRecycler.apply {
                            setHasFixedSize(true)
                            disableItemAnimator()
                            val lm = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                            layoutManager = lm
                            trendingRepoAdapter = TrendingRepoAdapter(processData(it.value))
                            adapter = trendingRepoAdapter
                            Log.i("TAG", "onActivityCreated: ${Gson().toJson(it.value)}")
                        }
                    }
                }
                is Resource.Failure -> handleApiError(it)
                else -> {
                    showDialog(true)
                }
            }
        }
    }

    private fun processData(responseBody: ResponseBody? = null): ArrayList<TrendingReposData> {
        return ArrayList<TrendingReposData>().apply {
            add(TrendingReposData("", "repo1"))
            add(TrendingReposData("", "repo2"))
            add(TrendingReposData("", "repo3"))
            add(TrendingReposData("", "repo4"))
            add(TrendingReposData("", "repo5"))
            add(TrendingReposData("", "repo6"))
            add(TrendingReposData("", "repo7"))
            add(TrendingReposData("", "repo8"))
            add(TrendingReposData("", "repo9"))
            add(TrendingReposData("", "repo10"))
            add(TrendingReposData("", "repo11"))
            add(TrendingReposData("", "repo12"))
            add(TrendingReposData("", "repo13"))
            add(TrendingReposData("", "repo14"))
            add(TrendingReposData("", "repo15"))
            add(TrendingReposData("", "repo16"))
            add(TrendingReposData("", "repo17"))
            add(TrendingReposData("", "repo18"))
            add(TrendingReposData("", "repo19"))
            add(TrendingReposData("", "repo20"))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun getViewModel(): Class<LandingViewModel> = LandingViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentLandingBinding = FragmentLandingBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): LandingRepository =
        LandingRepository(retrofitClient.buildApi(ApiService::class.java))

}