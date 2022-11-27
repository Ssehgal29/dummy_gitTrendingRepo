package com.dummy.trendinggitrepos.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dummy.trendinggitrepos.ui.landing.LandingRepository
import com.dummy.trendinggitrepos.ui.landing.LandingViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LandingViewModel::class.java) -> LandingViewModel(repository as LandingRepository) as T
            else -> throw IllegalArgumentException("view model class not found")
        }
    }
}