package com.itunessearch.android.presentation

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.itunessearch.android.presentation.main.MainFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AppNavHostFragment: NavHostFragment() {

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = fragmentFactory
    }
}