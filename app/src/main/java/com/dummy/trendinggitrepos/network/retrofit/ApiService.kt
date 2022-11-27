package com.dummy.trendinggitrepos.network.retrofit

import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {

    @GET("trending")
    suspend fun getTrendingRepos(): ResponseBody
}