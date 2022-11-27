package com.dummy.trendinggitrepos.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dummy.trendinggitrepos.network.Resource
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class LandingViewModel(private val landingRepository: LandingRepository) : ViewModel() {

    private val trendingReposLiveData: MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val trendingRepos: LiveData<Resource<ResponseBody>>
        get() = trendingReposLiveData

    fun getTrendingRepos() = viewModelScope.launch {
        trendingReposLiveData.value = Resource.Loading
        trendingReposLiveData.value = landingRepository.getTrendingRepos()
    }

}