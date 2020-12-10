package com.itunessearch.android.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.itunessearch.android.presentation.detail.DetailFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragmentFactory
@Inject
constructor(): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className) {
            MainFragment::class.java.name -> {
                MainFragment()
            }
            DetailFragment::class.java.name -> {
                DetailFragment()
            }
            else -> super.instantiate(classLoader, className)
        }
    }
}